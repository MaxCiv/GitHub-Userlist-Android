package com.maxciv.githubuserlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
@Parcelize
data class UserShortInfo(
        val id: Long,
        val login: String,
        val avatarUrl: String,
        val link: String
) : Parcelable
