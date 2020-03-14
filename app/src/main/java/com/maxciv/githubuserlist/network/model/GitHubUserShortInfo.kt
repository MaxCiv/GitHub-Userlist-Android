package com.maxciv.githubuserlist.network.model

import com.maxciv.githubuserlist.model.UserShortInfo
import com.squareup.moshi.Json

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
data class GitHubUserShortInfo(

        val id: Long,

        val login: String,

        @Json(name = "avatar_url")
        val avatarUrl: String,

        @Json(name = "html_url")
        val link: String
)

fun GitHubUserShortInfo.asDomainModel(): UserShortInfo {
    return UserShortInfo(
            id = id,
            login = login,
            avatarUrl = avatarUrl,
            link = link
    )
}

fun List<GitHubUserShortInfo>.asDomainModel(): List<UserShortInfo> {
    return map {
        it.asDomainModel()
    }
}
