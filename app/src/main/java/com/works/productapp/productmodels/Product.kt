package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("bilgiler")
    val bilgiler: List<Bilgiler>?,
    @SerializedName("durum")
    val durum: Boolean?,
    @SerializedName("mesaj")
    val mesaj: String?,
    @SerializedName("total")
    val total: String?
)