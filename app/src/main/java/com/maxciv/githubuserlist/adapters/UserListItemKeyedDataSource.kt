package com.maxciv.githubuserlist.adapters

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.maxciv.githubuserlist.model.LoadingStatus
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.GITHUB_USER_INITIAL_KEY
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListItemKeyedDataSource(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable,
        private val loadingStatus: MutableLiveData<LoadingStatus>
) : ItemKeyedDataSource<Long, UserShortInfo>() {

    private var initialParams: LoadInitialParams<Long>? = null
    private var initialCallback: LoadInitialCallback<UserShortInfo>? = null
    private var latestParams: LoadParams<Long>? = null
    private var latestCallback: LoadCallback<UserShortInfo>? = null

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<UserShortInfo>) {
        this.initialParams = params
        this.initialCallback = callback

        loadingStatus.postValue(LoadingStatus.LOADING)
        userRepository.getUsersInfo(params.requestedInitialKey ?: GITHUB_USER_INITIAL_KEY)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                            loadingStatus.postValue(LoadingStatus.LOADED)
                        },
                        {
                            Timber.e("ERROR loadInitial: ${it.message}")
                            loadingStatus.postValue(LoadingStatus.ERROR)
                        }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<UserShortInfo>) {
        this.latestParams = params
        this.latestCallback = callback

        loadingStatus.postValue(LoadingStatus.LOADING)
        userRepository.getUsersInfo(params.key)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                            loadingStatus.postValue(LoadingStatus.LOADED)
                        },
                        {
                            Timber.e("ERROR loadAfter: ${it.message}")
                            loadingStatus.postValue(LoadingStatus.ERROR)
                        }
                )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<UserShortInfo>) {
    }

    override fun getKey(item: UserShortInfo): Long {
        return item.id
    }

    fun retryLastLoad() {
        when {
            isLoadInitialReady() -> {
                loadInitial(initialParams ?: return, initialCallback ?: return)
            }
            isLoadAfterReady() -> {
                loadAfter(latestParams ?: return, latestCallback ?: return)
            }
        }
    }

    private fun isLoadInitialReady(): Boolean {
        return initialParams != null && initialCallback != null && !isLoadAfterReady()
    }

    private fun isLoadAfterReady(): Boolean {
        return latestParams != null && latestCallback != null
    }
}
