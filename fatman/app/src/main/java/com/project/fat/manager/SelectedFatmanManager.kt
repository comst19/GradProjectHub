package com.project.fat.manager

import android.content.Context
import android.util.Log
import com.project.fat.BuildConfig
import com.project.fat.dataStore.UserDataStore
import com.project.fat.dataStore.UserDataStore.dataStore

object SelectedFatmanManager {
    private var fatmanId : Long? = null
    private var fatmanImageUrl : String? = null
    private var isInit = false

    suspend fun initSelectedFatmanManager(context: Context){
        if(isInit)
            return
        try{
            context.dataStore.data.collect{
                val selectedFatmanId = it[UserDataStore.SELECTED_FATMAN_ID] ?: 1
                val selectedFatmanImageUrl = it[UserDataStore.SELECTED_FATMAN_IMAGE] ?: BuildConfig.default_fatman_image

                setSelectedFatman(selectedFatmanId, selectedFatmanImageUrl)
                isInit = true
            }
        }catch (e: Exception){
            Log.e("initSelectedFatmanManager", "error : ${e.message}", e)
        }
    }

    fun setSelectedFatman(id : Long, imageUrl : String){
        fatmanId = id
        fatmanImageUrl = imageUrl
    }

    fun getSelectedFatmanId() = fatmanId

    fun getSelectedFatmanImageUrl() = fatmanImageUrl
}