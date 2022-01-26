package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class İmage(
    @SerializedName("normal")
    val normal: String?,
    @SerializedName("thumb")
    val thumb: String?
)