package com.hackathon.zero.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.hackathon.zero.R
import com.hackathon.zero.databinding.DialogManagementPurposeBinding

class ManagementPurposeDialog(context: Context, private val completeClick: (Int) -> Unit): Dialog(context) {

    private lateinit var binding: DialogManagementPurposeBinding

    private var clickIndex = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_management_purpose, null, false)
        setContentView(binding.root)
        binding.sugarManagementLayout.setOnClickListener {
            clickIndex = 0
            binding.sugarManagementCheckView.visibility = View.VISIBLE
            binding.healthManagementCheckView.visibility = View.GONE
        }
        binding.healthManagementLayout.setOnClickListener {
            clickIndex = 1
            binding.sugarManagementCheckView.visibility = View.GONE
            binding.healthManagementCheckView.visibility = View.VISIBLE
        }
        binding.completeButton.setOnClickListener {
            completeClick(clickIndex)
            dismiss()
        }
    }
}