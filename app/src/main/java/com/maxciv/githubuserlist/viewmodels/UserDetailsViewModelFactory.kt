package com.maxciv.githubuserlist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * @author maxim.oleynik
 * @since 15.03.2020
 */
class UserDetailsViewModelFactory @Inject constructor(
        private val userDetailsViewModel: UserDetailsViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return userDetailsViewModel as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}
