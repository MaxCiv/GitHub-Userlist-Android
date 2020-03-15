package com.maxciv.githubuserlist.di

import com.maxciv.githubuserlist.network.GitHubUsersApi
import com.maxciv.githubuserlist.repository.GitHubUserRepository
import com.maxciv.githubuserlist.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author maxim.oleynik
 * @since 15.03.2020
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(usersApi: GitHubUsersApi): UserRepository {
        return GitHubUserRepository(usersApi)
    }
}
