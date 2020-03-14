package com.maxciv.githubuserlist.network

import com.maxciv.githubuserlist.network.model.GitHubUserCompleteInfo
import com.maxciv.githubuserlist.network.model.GitHubUserShortInfo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
interface GitHubUsersApi {

    @GET("/users")
    fun getAllUsers(@Query("since") since: Int): Observable<Response<List<GitHubUserShortInfo>>>

    @GET("/users/{login}")
    fun getUser(@Path("login") login: String): Observable<Response<GitHubUserCompleteInfo>>
}
