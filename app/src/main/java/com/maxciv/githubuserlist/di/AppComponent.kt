package com.maxciv.githubuserlist.di

import com.maxciv.githubuserlist.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * @author maxim.oleynik
 * @since 15.03.2020
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    UtilModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}
