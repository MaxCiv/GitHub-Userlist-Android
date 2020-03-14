package com.maxciv.githubuserlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.maxciv.githubuserlist.adapters.UserListDataSourceFactory
import com.maxciv.githubuserlist.model.UserShortInfo

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListViewModel : ViewModel() {

    val pagedList: LiveData<PagedList<UserShortInfo>>

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(30)
                .setPageSize(30)
                .build()

        pagedList = LivePagedListBuilder(UserListDataSourceFactory(), config).build()
    }
}
