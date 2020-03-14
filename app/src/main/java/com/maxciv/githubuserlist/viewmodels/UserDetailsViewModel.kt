package com.maxciv.githubuserlist.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxciv.githubuserlist.model.LoadingStatus
import com.maxciv.githubuserlist.model.User
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.ApiFactory
import com.maxciv.githubuserlist.repository.GitHubUserRepository
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserDetailsViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val userRepository: UserRepository = GitHubUserRepository(ApiFactory.gitHubUsersApi)

    var userShortInfo: UserShortInfo? = null

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private fun setNewUser(user: User) {
        _user.value = user
    }

    //region LoadingStatus
    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    private fun isUserLoading(): Boolean {
        return loadingStatus.value == LoadingStatus.LOADING
    }

    private fun userStartLoading() {
        _loadingStatus.value = LoadingStatus.LOADING
    }

    private fun userLoaded() {
        _loadingStatus.value = LoadingStatus.LOADED
    }

    private fun userLoadFailed() {
        _loadingStatus.value = LoadingStatus.ERROR
    }
    //endregion

    @SuppressLint("CheckResult")
    fun loadUser() {
        if (isUserLoading()) return

        userStartLoading()
        userRepository.getUser(userShortInfo?.login ?: "")
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user: User ->
                            setNewUser(user)
                            userLoaded()
                        },
                        {
                            Timber.e("ERROR getUser: ${it.message}")
                            userLoadFailed()
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
