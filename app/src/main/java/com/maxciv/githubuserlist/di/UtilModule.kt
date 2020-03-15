package com.maxciv.githubuserlist.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * @author maxim.oleynik
 * @since 15.03.2020
 */
@Module
class UtilModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
