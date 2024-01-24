package com.hackathon.zero.presentation.inputinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.zero.data.UserInfoInput
import com.hackathon.zero.domain.UserInfoRepository
import com.hackathon.zero.domain.use_case.PostUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputInfo2ViewModelImpl @Inject constructor(
    private val postUserInfoUseCase: PostUserInfoUseCase
) : ViewModel(), InputInfo2ViewModel {

    override val seekbarValue: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _managePurpose: MutableStateFlow<String> = MutableStateFlow("당뇨 관리 (혈당 관리)")
    override val managePurpose: StateFlow<String> get() = _managePurpose

    private val _showDialog: MutableSharedFlow<Boolean> = MutableSharedFlow()
    override val showDialog: SharedFlow<Boolean> get() = _showDialog

    private val _moveTo: MutableSharedFlow<Boolean> = MutableSharedFlow()
    override val moveTo: SharedFlow<Boolean> get() = _moveTo
    override var gender: String? = null
    override var weight: Int? = null
    override var height: Int? = null
    override var name: String? = null
    override var age: Int? = null
    private var selectManagementItemIndex = 0
    private val purposeList = listOf("당뇨 관리 (혈당 관리)", "체중 조절 및 건강 관리")

    override fun doneClicked() {
        viewModelScope.launch {
            if(weight != null && name != null && height != null && gender != null && age != null) {
                val user = UserInfoInput(
                    name = name!!,
                    weight = weight!!,
                    height = height!!,
                    gender = gender!!,
                    age = age!!,
                    purposeType = (selectManagementItemIndex + 1).toString(),
                    activityType = (seekbarValue.value + 1).toString()
                )
                Log.e("user", user.toString())
                postUserInfoUseCase(UserInfoInput(
                    name = name!!,
                    weight = weight!!,
                    height = height!!,
                    gender = gender!!,
                    age = age!!,
                    purposeType = (selectManagementItemIndex + 1).toString(),
                    activityType = (seekbarValue.value + 1).toString()
                )).collect {
                    Log.e("it", it.data.toString())
                    if(it.data != null) _moveTo.emit(true)
                }
            }
        }
    }

    override fun manageClick() {
        viewModelScope.launch { _showDialog.emit(true) }
    }

    override fun selectedManagementItem(index: Int) {
        selectManagementItemIndex = index
        if(index in 0..1) {
            viewModelScope.launch {
                runCatching {
                    _managePurpose.emit(purposeList[index])
                }.onFailure {
                    it.printStackTrace()
                    _managePurpose.emit(purposeList[0])
                }

            }
        }
    }
}