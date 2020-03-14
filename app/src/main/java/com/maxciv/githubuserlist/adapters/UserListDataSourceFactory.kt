package com.maxciv.githubuserlist.adapters

import androidx.paging.DataSource
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
class UserListDataSourceFactory(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Long, UserShortInfo>() {

    override fun create(): DataSource<Long, UserShortInfo> {
        return UserListItemKeyedDataSource(userRepository, compositeDisposable)
    }
}
