package com.hackathon.zero.presentation.inputinfo

import android.content.Intent
import com.hackathon.zero.data.UserInfoInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface InputInfo2ViewModel {
    val seekbarValue: MutableStateFlow<Int>
    val managePurpose: StateFlow<String>
    val showDialog: SharedFlow<Boolean>
    val moveTo: SharedFlow<Boolean>
    var gender: String?
    var weight: Int?
    var height: Int?
    var name: String?
    var age: Int?

    fun doneClicked()
    fun manageClick()
    fun selectedManagementItem(index: Int)
}