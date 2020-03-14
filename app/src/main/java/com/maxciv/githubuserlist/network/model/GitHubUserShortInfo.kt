package com.maxciv.githubuserlist.network.model

import com.maxciv.githubuserlist.model.UserShortInfo
import com.squareup.moshi.Json

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
data class GitHubUserShortInfo(

        var id: Long = 0L,

        var login: String = "",

        @Json(name = "avatar_url")
        var avatarUrl: String = ""
)

fun GitHubUserShortInfo.asDomainModel(): UserShortInfo {
    return UserShortInfo(
            id = id,
            login = login,
            avatarUrl = avatarUrl
    )
}

fun List<GitHubUserShortInfo>.asDomainModel(): List<UserShortInfo> {
    return map {
        it.asDomainModel()
    }
}
