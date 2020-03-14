package com.maxciv.githubuserlist.model

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
data class User(
        var id: Long = 0L,
        var login: String = "",
        var avatarUrl: String = "",
        var name: String = "",
        var location: String = "",
        var link: String = ""
)
