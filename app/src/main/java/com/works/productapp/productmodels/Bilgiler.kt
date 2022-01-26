package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class Bilgiler(
    @SerializedName("brief")
    val brief: String?,
    @SerializedName("campaign")
    val campaign: Campaign?,
    @SerializedName("campaignDescription")
    val campaignDescription: String?,
    @SerializedName("campaignTitle")
    val campaignTitle: String?,
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: Boolean?,
    @SerializedName("images")
    val images: List<İmage>?,
    @SerializedName("likes")
    val likes: Likes?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("productId")
    val productId: String?,
    @SerializedName("productName")
    val productName: String?,
    @SerializedName("saleInformation")
    val saleInformation: SaleInformation?
)