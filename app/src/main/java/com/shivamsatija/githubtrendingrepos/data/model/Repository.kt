package com.shivamsatija.githubtrendingrepos.data.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("rank")
    val rank: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("repositoryName")
    val repositoryName: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("languageColor")
    val languageColor: String? = null,
    @SerializedName("totalStars")
    val totalStars: Int? = null,
    @SerializedName("forks")
    val forks: Int? = null,
    @SerializedName("starsSince")
    val starsSince: Int? = null,
    @SerializedName("since")
    val since: String? = null,
    @SerializedName("builtBy")
    val collaborators: List<Collaborator>? = null
) {
    fun getId() : String = "$username$repositoryName"
}