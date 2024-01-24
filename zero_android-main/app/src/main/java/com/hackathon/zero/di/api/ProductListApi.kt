package com.hackathon.zero.di.api

import com.hackathon.zero.core.dto.ResponseBody
import com.hackathon.zero.data.Product
import com.hackathon.zero.data.ProductItem
import com.hackathon.zero.data.ProductSearchItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductListApi {

    @GET("api/v1/product")
    suspend fun getProductList(
        @Query("keyword") keyword: String,
        @Query("lastProductId") lastProductId: Int? = null
    ): ResponseBody<ProductItem?>
}