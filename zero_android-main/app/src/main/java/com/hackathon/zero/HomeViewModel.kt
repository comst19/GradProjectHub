package com.hackathon.zero

import android.content.Intent
import androidx.lifecycle.LiveData
import com.hackathon.zero.data.Product
import com.hackathon.zero.data.ProductSearchItem
import com.hackathon.zero.data.UserInfoInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val isLoading: StateFlow<Boolean>
    val errorMessage: StateFlow<String?>
    val profileName: StateFlow<String>
    val sugar: StateFlow<Double>
    val calories: StateFlow<Int>
    val stamps: StateFlow<List<Boolean>>
    val sharedAction: SharedFlow<Intent>
    var query: MutableStateFlow<String>
    val productList: LiveData<List<ProductSearchItem?>>

    fun sharedClicked()

    fun getUserInfo()
    fun listUpdate(position: Int)

    fun queryChanged()
}