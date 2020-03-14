package com.maxciv.githubuserlist.network

import com.maxciv.githubuserlist.network.model.GitHubUserCompleteInfo
import com.maxciv.githubuserlist.network.model.GitHubUserShortInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
const val GITHUB_USER_INITIAL_KEY = 0L
const val GITHUB_USER_PAGE_SIZE = 30

interface GitHubUsersApi {

    @GET("/users")
    fun getAllUsers(@Query("since") since: Long): Single<Response<List<GitHubUserShortInfo>>>

    @GET("/users/{login}")
    fun getUser(@Path("login") login: String): Single<Response<GitHubUserCompleteInfo>>
}
