package com.maxciv.githubuserlist.adapters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.maxciv.githubuserlist.model.LoadingStatus
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
class UserListDataSourceFactory(
        private val userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable,
        private val pagedListLoadingStatus: MutableLiveData<LoadingStatus>
) : DataSource.Factory<Long, UserShortInfo>() {

    lateinit var dataSource: UserListItemKeyedDataSource
        private set

    override fun create(): DataSource<Long, UserShortInfo> {
        dataSource = UserListItemKeyedDataSource(userRepository, compositeDisposable, pagedListLoadingStatus)
        return dataSource
    }
}
