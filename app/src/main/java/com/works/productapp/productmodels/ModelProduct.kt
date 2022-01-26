package com.works.productapp.productmodels


import com.google.gson.annotations.SerializedName

data class ModelProduct(
    @SerializedName("Products")
    val products: List<Product>?
)