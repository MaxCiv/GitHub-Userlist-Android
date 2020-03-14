package com.maxciv.githubuserlist.model

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
data class UserShortInfo(
        var id: Long = 0L,
        var login: String = "",
        var avatarUrl: String = ""
)