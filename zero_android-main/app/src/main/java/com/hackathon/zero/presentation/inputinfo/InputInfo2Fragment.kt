package com.hackathon.zero.presentation.inputinfo

import android.os.Bundle
import android.util.Log
import android.view.View
import com.hackathon.zero.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hackathon.zero.base.BaseFragment
import com.hackathon.zero.data.UserInfoInput
import com.hackathon.zero.databinding.FragmentInputInfo2Binding
import com.hackathon.zero.presentation.dialog.ManagementPurposeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InputInfo2Fragment : BaseFragment<FragmentInputInfo2Binding>(R.layout.fragment_input_info2) {

    private val viewModel: InputInfo2ViewModel by viewModels<InputInfo2ViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.gender = if(arguments?.getInt("gender") == 0) "MAN" else "WOMAN"
        viewModel.height = arguments?.getInt("height")
        viewModel.weight = arguments?.getInt(("weight"))
        viewModel.age = arguments?.getInt("age")
        viewModel.name = arguments?.getString("name")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showDialog.collect {
                if(it) ManagementPurposeDialog(requireContext()) { index ->
                    viewModel.selectedManagementItem(index)
                }.show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moveTo.collect {
                if(it) findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}