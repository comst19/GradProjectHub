package com.hackathon.zero.domain

import com.hackathon.zero.core.dto.ResponseBody
import com.hackathon.zero.data.Product
import com.hackathon.zero.data.ProductItem
import com.hackathon.zero.data.ProductSearchItem

interface ProductRepository {

    suspend fun getProductList(keyword: String, lastProductId: Int? = null): ResponseBody<ProductItem?>
}