package com.shivamsatija.githubtrendingrepos.data.model

import com.google.gson.annotations.SerializedName

data class Collaborator(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null
)
