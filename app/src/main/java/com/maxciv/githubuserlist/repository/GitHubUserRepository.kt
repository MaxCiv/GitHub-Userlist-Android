package com.maxciv.githubuserlist.repository

import com.maxciv.githubuserlist.model.User
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.GitHubUsersApi
import com.maxciv.githubuserlist.network.model.asDomainModel
import io.reactivex.Observable

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
class GitHubUserRepository(private val api: GitHubUsersApi): UserRepository {

    override fun getUsersInfo(since: Int): Observable<List<UserShortInfo>> {
        return api.getAllUsers(since).map {
            it.body()?.asDomainModel() ?: listOf()
        }
    }

    override fun getUser(login: String): Observable<User> {
        return api.getUser(login).map {
            it.body()?.asDomainModel() ?: User()
        }
    }
}
