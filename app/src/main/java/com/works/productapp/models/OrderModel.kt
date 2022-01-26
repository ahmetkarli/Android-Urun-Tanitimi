package com.works.productapp.models


import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("order")
    val order: List<Order?>?
)

data class Order(
    @SerializedName("durum")
    val durum: Boolean?,
    @SerializedName("mesaj")
    val mesaj: String?
    )
