package com.hackathon.zero.domain.use_case

import android.util.Log
import com.hackathon.zero.core.Resource
import com.hackathon.zero.data.Product
import com.hackathon.zero.data.ProductItem
import com.hackathon.zero.data.ProductSearchItem
import com.hackathon.zero.domain.ProductRepository
import com.hackathon.zero.util.Constants
import com.hackathon.zero.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(keyword: String, lastProductId : Int): Flow<Resource<ProductItem?>> = flow {
        runCatching {
            emit(Resource.loading())
            val response = productRepository.getProductList(keyword, lastProductId)
            Log.e("여기도", response.status.toString())
            if(isSuccessful(response.status)) {
                Log.d("usecase", "${response.result}")
                emit(Resource.success(response.result))
            } else {
                emit(Resource.error(response.message))
            }
        }.onFailure {
            it.printStackTrace()
            emit(Resource.error(it.localizedMessage ?: Constants.ERROR_UNKNOWN))
        }
    }

}