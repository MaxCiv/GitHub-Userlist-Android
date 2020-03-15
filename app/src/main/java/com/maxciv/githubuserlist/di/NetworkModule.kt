package com.maxciv.githubuserlist.di

import com.maxciv.githubuserlist.network.GITHUB_BASE_URL
import com.maxciv.githubuserlist.network.GitHubUsersApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGitHubUserApi(retrofit: Retrofit): GitHubUsersApi {
        return retrofit.create(GitHubUsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }
}
