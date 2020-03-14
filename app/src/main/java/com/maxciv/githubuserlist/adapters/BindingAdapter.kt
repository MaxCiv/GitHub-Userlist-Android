package com.maxciv.githubuserlist.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * @author maxim.oleynik
 * @since 14.03.2020
 */
@BindingAdapter("nickname")
fun TextView.setNickName(nickname: String?) {
    text = nickname?.let { "@$it" }
}

@BindingAdapter("visibilityByText")
fun TextView.setVisibilityByText(text: String?) {
    visibility = if (text.isNullOrBlank()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
