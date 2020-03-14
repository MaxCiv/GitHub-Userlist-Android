package com.maxciv.githubuserlist.adapters

import android.annotation.SuppressLint
import androidx.paging.ItemKeyedDataSource
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.GITHUB_USER_INITIAL_KEY
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListItemKeyedDataSource(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Long, UserShortInfo>() {

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<UserShortInfo>) {
        userRepository.getUsersInfo(params.requestedInitialKey ?: GITHUB_USER_INITIAL_KEY)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                        },
                        {
                            Timber.e("ERROR loadInitial: ${it.message}")
                        }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<UserShortInfo>) {
        userRepository.getUsersInfo(params.key)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                        },
                        {
                            Timber.e("ERROR loadAfter: ${it.message}")
                        }
                )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<UserShortInfo>) {
    }

    override fun getKey(item: UserShortInfo): Long {
        return item.id
    }
}
