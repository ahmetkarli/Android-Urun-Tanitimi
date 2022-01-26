package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class Likes(
    @SerializedName("dislike")
    val dislike: Int?,
    @SerializedName("like")
    val like: Like?
)