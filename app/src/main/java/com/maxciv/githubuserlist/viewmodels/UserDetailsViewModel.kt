package com.maxciv.githubuserlist.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxciv.githubuserlist.model.LoadingStatus
import com.maxciv.githubuserlist.model.User
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.model.isLoading
import com.maxciv.githubuserlist.model.setError
import com.maxciv.githubuserlist.model.setLoaded
import com.maxciv.githubuserlist.model.setLoading
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserDetailsViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    var userShortInfo: UserShortInfo? = null

    private val _userLoadingStatus = MutableLiveData<LoadingStatus>()
    val userLoadingStatus: LiveData<LoadingStatus> = _userLoadingStatus

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private fun setNewUser(user: User) {
        _user.value = user
    }

    @SuppressLint("CheckResult")
    fun loadUser() {
        if (userLoadingStatus.isLoading()) return

        _userLoadingStatus.setLoading()
        userRepository.getUser(userShortInfo?.login ?: "")
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user: User ->
                            setNewUser(user)
                            _userLoadingStatus.setLoaded()
                        },
                        {
                            Timber.e("ERROR getUser: ${it.message}")
                            _userLoadingStatus.setError()
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
