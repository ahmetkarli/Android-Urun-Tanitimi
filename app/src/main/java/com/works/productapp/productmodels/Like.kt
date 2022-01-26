package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class Like(
    @SerializedName("ortalama")
    val ortalama: String?,
    @SerializedName("oy_toplam")
    val oyToplam: String?
)