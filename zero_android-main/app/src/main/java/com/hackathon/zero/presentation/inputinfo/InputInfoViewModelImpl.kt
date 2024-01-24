package com.hackathon.zero.presentation.inputinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.zero.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InputInfoViewModelImpl: ViewModel(), InputInfoViewModel {

    override val name: MutableStateFlow<String> = MutableStateFlow("")

    private val _isGenderSelectMan: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override val isGenderSelectMan: StateFlow<Boolean> get() = _isGenderSelectMan

    override val weight: MutableStateFlow<String> = MutableStateFlow("")
    override val height: MutableStateFlow<String> = MutableStateFlow("")
    override val age: MutableStateFlow<String> = MutableStateFlow("")
    private val _moveTo: MutableSharedFlow<Int> = MutableSharedFlow()
    override val moveTo: SharedFlow<Int> get() = _moveTo


    override fun manClicked() {
        viewModelScope.launch { _isGenderSelectMan.emit(true) }
    }

    override fun womanClicked() {
        viewModelScope.launch { _isGenderSelectMan.emit(false) }
    }

    override fun nextClicked() {
        viewModelScope.launch { _moveTo.emit(R.id.action_inputInfoFragment_to_inputInfo2Fragment) }
    }
}