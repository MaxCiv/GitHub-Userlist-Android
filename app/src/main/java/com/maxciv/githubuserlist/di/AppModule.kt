package com.maxciv.githubuserlist.di

import com.maxciv.githubuserlist.MainActivity
import com.maxciv.githubuserlist.ui.UserDetailsFragment
import com.maxciv.githubuserlist.ui.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author maxim.oleynik
 * @since 15.03.2020
 */
@Suppress("unused")
@Module
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun bindUserDetailsFragment(): UserDetailsFragment
}
