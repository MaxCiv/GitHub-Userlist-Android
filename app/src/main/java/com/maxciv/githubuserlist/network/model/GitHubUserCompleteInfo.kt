package com.maxciv.githubuserlist.network.model

import com.maxciv.githubuserlist.model.User
import com.squareup.moshi.Json

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
data class GitHubUserCompleteInfo(

        val id: Long,

        val login: String,

        @Json(name = "avatar_url")
        val avatarUrl: String,

        val name: String?,

        val location: String?,

        @Json(name = "html_url")
        val link: String
)

fun GitHubUserCompleteInfo.asDomainModel(): User {
    return User(
            id = id,
            login = login,
            avatarUrl = avatarUrl,
            name = name ?: "",
            location = location ?: "",
            link = link
    )
}
