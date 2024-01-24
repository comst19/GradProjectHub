package com.project.fat.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.project.fat.data.store.StoreAvata

object UserDataStore{
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

    val USER_ID = longPreferencesKey("user_id")
    val NAME = stringPreferencesKey("name")
    val SELECTED_FATMAN_ID = longPreferencesKey("selected_fatman_id")
    val SELECTED_FATMAN_IMAGE = stringPreferencesKey("selected_fatman_image")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val MONEY = longPreferencesKey("money")

    suspend fun saveSelectedFatman(context: Context, selectedFatman : StoreAvata){
        try {
            context.dataStore.edit {
                Log.d("saveSelectedFatman", "edit start")
                it[SELECTED_FATMAN_ID] = selectedFatman.id
                Log.d("saveSelectedFatman", "set id")
                it[SELECTED_FATMAN_IMAGE] = selectedFatman.fatmanImage
                Log.d("saveSelectedFatman", "edit end")
            }
        }catch (e: Exception){
            Log.e("saveSelectedFatman", "error : ${e.message}", e)
        }
    }
}

