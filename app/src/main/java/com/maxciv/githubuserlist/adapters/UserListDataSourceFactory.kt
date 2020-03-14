package com.maxciv.githubuserlist.adapters

import androidx.paging.DataSource
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.ApiFactory
import com.maxciv.githubuserlist.repository.GitHubUserRepository

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
class UserListDataSourceFactory : DataSource.Factory<Int, UserShortInfo>() {

    override fun create(): DataSource<Int, UserShortInfo> {
        return UserListDataSource(GitHubUserRepository(ApiFactory.gitHubUsersApi))
    }
}
