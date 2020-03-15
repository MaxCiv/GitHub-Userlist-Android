package com.maxciv.githubuserlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.maxciv.githubuserlist.adapters.UserListDataSourceFactory
import com.maxciv.githubuserlist.model.LoadingStatus
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.GITHUB_USER_INITIAL_KEY
import com.maxciv.githubuserlist.network.GITHUB_USER_PAGE_SIZE
import com.maxciv.githubuserlist.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
class UserListViewModel @Inject constructor(
        userRepository: UserRepository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val dataSourceFactory: UserListDataSourceFactory
    val pagedList: LiveData<PagedList<UserShortInfo>>

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

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(GITHUB_USER_PAGE_SIZE)
                .setPageSize(GITHUB_USER_PAGE_SIZE)
                .build()

        dataSourceFactory = UserListDataSourceFactory(userRepository, compositeDisposable, _loadingStatus)
        pagedList = LivePagedListBuilder(dataSourceFactory, config)
                .setInitialLoadKey(GITHUB_USER_INITIAL_KEY)
                .build()
    }

    fun retryLoadPagedList() {
        if (isUserLoading()) return

        dataSourceFactory.dataSource.retryLastLoad()
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
