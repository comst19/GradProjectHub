package com.hackathon.zero.presentation.inputinfo

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface InputInfoViewModel {

    val name: MutableStateFlow<String>
    val isGenderSelectMan: StateFlow<Boolean>
    val weight: MutableStateFlow<String>
    val height: MutableStateFlow<String>
    val age: MutableStateFlow<String>
    val moveTo: SharedFlow<Int>

    fun manClicked()
    fun womanClicked()
    fun nextClicked()
}