package com.maxciv.githubuserlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.maxciv.githubuserlist.adapters.UserListDataSourceFactory
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.ApiFactory
import com.maxciv.githubuserlist.network.GITHUB_USER_INITIAL_KEY
import com.maxciv.githubuserlist.network.GITHUB_USER_PAGE_SIZE
import com.maxciv.githubuserlist.repository.GitHubUserRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val pagedList: LiveData<PagedList<UserShortInfo>>

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(GITHUB_USER_PAGE_SIZE)
                .setPageSize(GITHUB_USER_PAGE_SIZE)
                .build()

        pagedList = LivePagedListBuilder(UserListDataSourceFactory(GitHubUserRepository(ApiFactory.gitHubUsersApi), compositeDisposable), config)
                .setInitialLoadKey(GITHUB_USER_INITIAL_KEY)
                .build()
    }

    //region navigateToUserDetailsEvent
    private val _navigateToUserDetailsEvent = MutableLiveData<UserShortInfo>()
    val navigateToUserDetailsEvent: LiveData<UserShortInfo> = _navigateToUserDetailsEvent

    fun navigateToUserDetails(userShortInfo: UserShortInfo) {
        _navigateToUserDetailsEvent.value = userShortInfo
    }

    fun navigateToUserDetailsEnded() {
        _navigateToUserDetailsEvent.value = null
    }
    //endregion

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
