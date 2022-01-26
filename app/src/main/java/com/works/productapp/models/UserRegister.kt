package com.works.productapp.models


import com.google.gson.annotations.SerializedName

data class UserRegister(
    @SerializedName("user")
    val user: List<UserDatas?>?
)
    data class UserDatas(
        @SerializedName("durum")
        val durum: Boolean?,
        @SerializedName("kullaniciId")
        val kullaniciId: String?,
        @SerializedName("mesaj")
        val mesaj: String?
    )
