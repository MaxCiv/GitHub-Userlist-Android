package com.maxciv.githubuserlist.adapters

import android.annotation.SuppressLint
import androidx.paging.PositionalDataSource
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.repository.UserRepository
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListDataSource(
        private val userRepository: UserRepository
//        private val compositeDisposable: CompositeDisposable
) : PositionalDataSource<UserShortInfo>() {

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<UserShortInfo>) {
        userRepository.getUsersInfo(params.requestedStartPosition)
                .doOnSubscribe { disposable ->
//                    compositeDisposable.add(disposable)
                }
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList, 0)
                        },
                        {
                            Timber.e("ERROR loadInitial: ${it.message}")
                        }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<UserShortInfo>) {
        userRepository.getUsersInfo(params.startPosition)
                .doOnSubscribe { disposable ->
//                    compositeDisposable.add(disposable)
                }
                .subscribe(
                        { usersInfoList: List<UserShortInfo> ->
                            callback.onResult(usersInfoList)
                        },
                        {
                            Timber.e("ERROR loadRange: ${it.message}")
                        }
                )
    }
}
