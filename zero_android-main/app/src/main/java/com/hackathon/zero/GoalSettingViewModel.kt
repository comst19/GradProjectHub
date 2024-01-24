package com.hackathon.zero

import kotlinx.coroutines.flow.StateFlow

interface GoalSettingViewModel {
    val userName: StateFlow<String?>
    val userGoal: StateFlow<String>

    fun okClick()
}