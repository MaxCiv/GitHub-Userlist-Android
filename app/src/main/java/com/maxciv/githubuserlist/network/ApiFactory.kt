package com.maxciv.githubuserlist.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
object ApiFactory {

    private const val GITHUB_BASE_URL = "https://api.github.com/"

    private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    val gitHubUsersApi: GitHubUsersApi = retrofit().create(GitHubUsersApi::class.java)

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }
}
