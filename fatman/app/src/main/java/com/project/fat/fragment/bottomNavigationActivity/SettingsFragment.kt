package com.project.fat.fragment.bottomNavigationActivity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.project.fat.databinding.FragmentSettingsBinding
import com.project.fat.LoginActivity
import com.project.fat.dataStore.UserDataStore
import com.project.fat.manager.TokenManager
import kotlinx.coroutines.launch
import com.project.fat.dataStore.UserDataStore.dataStore

class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val dataStore by lazy {
        requireContext().dataStore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)


        binding.btnLogout.setOnClickListener {
            clearAccessTokenFromDataStore()
            Log.d("엑세스 토큰 지우기", "${UserDataStore.ACCESS_TOKEN}\n${UserDataStore.REFRESH_TOKEN}")
            moveLoginActivity()
        }

        return binding.root
    }
    private fun clearAccessTokenFromDataStore() {
        lifecycleScope.launch {
            this@SettingsFragment.dataStore.edit { preferences ->
                preferences.remove(UserDataStore.ACCESS_TOKEN)
                preferences.remove(UserDataStore.REFRESH_TOKEN)
            }
        }
    }
    private fun moveLoginActivity(){
        val intent = Intent(context, LoginActivity()::class.java)
        intent.putExtra("logoutState", false)
        startActivity(intent)
    }

    //로그아웃
    
    private fun googleLogout() {
        val intent = Intent(context, LoginActivity()::class.java)
        intent.putExtra("logoutState", false)
        startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}