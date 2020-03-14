package com.maxciv.githubuserlist.model

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
data class User(
        val id: Long,
        val login: String,
        val avatarUrl: String,
        val name: String,
        val location: String,
        val link: String
)
