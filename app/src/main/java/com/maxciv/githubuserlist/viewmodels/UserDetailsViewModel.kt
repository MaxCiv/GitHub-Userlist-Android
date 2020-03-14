package com.maxciv.githubuserlist.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    @SuppressLint("CheckResult")
    fun load() {
        userRepository.getUser(userShortInfo?.login ?: "")
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user: User ->
                            setNewUser(user)
                        },
                        {
                            Timber.e("ERROR getUser: ${it.message}")
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
