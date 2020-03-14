package com.maxciv.githubuserlist.repository

import com.maxciv.githubuserlist.model.User
import com.maxciv.githubuserlist.model.UserShortInfo
import io.reactivex.Observable

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
interface UserRepository {

    fun getUsersInfo(since: Int): Observable<List<UserShortInfo>>

    fun getUser(login: String): Observable<User>
}
