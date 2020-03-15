package com.maxciv.githubuserlist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * @author maxim.oleynik
 * @since 15.03.2020
 */
class UserListViewModelFactory @Inject constructor(
        private val userListViewModel: UserListViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return userListViewModel as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}
