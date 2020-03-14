package com.maxciv.githubuserlist.network.model

import com.maxciv.githubuserlist.model.User
import com.squareup.moshi.Json

/**
 * @author maxim.oleynik
 * @since 13.03.2020
 */
data class GitHubUserCompleteInfo(

        var id: Long = 0L,

        var login: String = "",

        @Json(name = "avatar_url")
        var avatarUrl: String = "",

        var name: String = "",

        var location: String = "",

        @Json(name = "html_url")
        var link: String = ""
)

fun GitHubUserCompleteInfo.asDomainModel(): User {
    return User(
            id = id,
            login = login,
            avatarUrl = avatarUrl,
            name = name,
            location = location,
            link = link
    )
}
