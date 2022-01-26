package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class Campaign(
    @SerializedName("campaignType")
    val campaignType: String?,
    @SerializedName("campaignTypeId")
    val campaignTypeId: String?
)