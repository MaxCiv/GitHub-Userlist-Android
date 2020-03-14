package com.maxciv.githubuserlist.repository

import com.maxciv.githubuserlist.model.User
import com.maxciv.githubuserlist.model.UserShortInfo
import io.reactivex.Single

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
interface UserRepository {

    fun getUsersInfo(since: Long): Single<List<UserShortInfo>>

    fun getUser(login: String): Single<User>
}
