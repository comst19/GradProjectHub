package com.hackathon.zero.data

import com.google.gson.annotations.SerializedName


data class ProductItem(
    @SerializedName("productInfoList") val productInfoList: List<Product>
)

data class Product(
    @SerializedName("productName") val productName: String,
    @SerializedName("productSugar") val productSugar: Double,
    @SerializedName("productKcal") val productCalorie: Int,
    @SerializedName("productSize") val productCapacity: Int,
    @SerializedName("lastProductId") val lastProductId: Int
)