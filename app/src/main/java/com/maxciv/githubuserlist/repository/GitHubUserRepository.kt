package com.maxciv.githubuserlist.repository

import com.maxciv.githubuserlist.model.User
import com.maxciv.githubuserlist.model.UserShortInfo
import com.maxciv.githubuserlist.network.GitHubUsersApi
import com.maxciv.githubuserlist.network.model.asDomainModel
import io.reactivex.Single

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
class GitHubUserRepository(private val api: GitHubUsersApi) : UserRepository {

    override fun getUsersInfo(since: Long): Single<List<UserShortInfo>> {
        return api.getAllUsers(since).flatMap { response ->
            if (response.isSuccessful && response.body() != null) {
                Single.just(response.body()?.asDomainModel())
            } else {
                Single.error(Throwable(response.errorBody()?.string()))
            }
        }
    }

    override fun getUser(login: String): Single<User> {
        return api.getUser(login).flatMap { response ->
            if (response.isSuccessful && response.body() != null) {
                Single.just(response.body()?.asDomainModel())
            } else {
                Single.error(Throwable(response.errorBody()?.string()))
            }
        }
    }
}
