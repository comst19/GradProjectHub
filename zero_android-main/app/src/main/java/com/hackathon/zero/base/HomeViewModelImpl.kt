package com.hackathon.zero.base

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.zero.HomeViewModel
import com.hackathon.zero.data.ProductSearchItem
import com.hackathon.zero.domain.use_case.GetHomeDataUseCase
import com.hackathon.zero.domain.use_case.GetProductListUseCase
import com.hackathon.zero.domain.use_case.GetUserInfoUseCase
import com.hackathon.zero.util.Constants.USER_ID
import com.hackathon.zero.util.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getProductListUseCase: GetProductListUseCase,
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val sp: SharedPreferencesUtil
): ViewModel(), HomeViewModel {

    init {
        getUserInfo()
    }

    private var stampMap = listOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    override val errorMessage: StateFlow<String?>
        get() = _errorMessage

    private val _profileName : MutableStateFlow<String> = MutableStateFlow(UNREGISTERED_USER)
    override val profileName: StateFlow<String>
        get() = _profileName

    private val _sugar: MutableStateFlow<Double> = MutableStateFlow(0.0)
    override val sugar: StateFlow<Double>
        get() = _sugar

    private val _calories: MutableStateFlow<Int> = MutableStateFlow(0)
    override val calories: StateFlow<Int>
        get() = _calories

    private val _stamps = MutableStateFlow(stampMap)
    override val stamps: StateFlow<List<Boolean>>
        get() = _stamps

    override var query = MutableStateFlow<String>("")

    private val _sharedAction: MutableSharedFlow<Intent> = MutableSharedFlow()
    override val sharedAction: SharedFlow<Intent>
        get() = _sharedAction

    private val _productList: MutableLiveData<List<ProductSearchItem?>> = MutableLiveData(
        mutableListOf()
    )
    override val productList: LiveData<List<ProductSearchItem?>>
        get() = _productList

    override fun getUserInfo() {
        val userId = sp.getInt(USER_ID, USER_ID_NONE)
        Log.e("userId", userId.toString())
        if (userId != USER_ID_NONE) postUserInfoValue(userId) else return
    }

    override fun listUpdate(position: Int) {
        val result = productList.value?.filterNotNull()?.map { it.copy() }
        Log.e("하...", result.toString())
        if(result != null) {
            _productList.value = result.mapIndexed { index, item ->
                item.isSelect = index == position
                item
            }
        }
    }

    override fun sharedClicked() {

    }

    override fun queryChanged() {
        viewModelScope.launch {
            getProductListUseCase(query.value, 0).collect {
                Log.d("ddd", it.data.toString())
                if (it.data != null) {
                    _productList.value = it.data.productInfoList.map {  data -> ProductSearchItem(data, false) }
                }
            }
        }
    }

    private fun postUserInfoValue(userId: Int) {
        viewModelScope.launch {
            getHomeDataUseCase(userId).collect {
                Log.e("it", it.data.toString())
                if(it.data != null) {
                    _profileName.value = it.data.username
                    _calories.value = it.data.totalKcal.toInt()
                    _sugar.value = it.data.totalSugar.toDouble()
                }
            }
        }
    }




    companion object {
        const val UNREGISTERED_USER = "User"
        const val USER_ID_NONE = 0
    }
}