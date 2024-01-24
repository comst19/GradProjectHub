package com.hackathon.zero.data.repository

import com.hackathon.zero.core.dto.ResponseBody
import com.hackathon.zero.data.Product
import com.hackathon.zero.data.ProductItem
import com.hackathon.zero.data.ProductSearchItem
import com.hackathon.zero.di.api.ProductListApi
import com.hackathon.zero.domain.ProductRepository

class ProductRepositoryImpl(
    private val api : ProductListApi
): ProductRepository {
    override suspend fun getProductList(
        keyword: String,
        lastProductId: Int?
    ): ResponseBody<ProductItem?> {
        return api.getProductList(keyword, lastProductId)
    }
}