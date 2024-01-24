package com.hackathon.zero.presentation.inputinfo

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hackathon.zero.R
import com.hackathon.zero.base.BaseFragment
import com.hackathon.zero.databinding.FragmentInputInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InputInfoFragment : BaseFragment<FragmentInputInfoBinding>(R.layout.fragment_input_info) {

    private val viewModel: InputInfoViewModel by viewModels<InputInfoViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moveTo.collect {
                runCatching {
                    findNavController().navigate(
                        it,
                        bundleOf(
                            "name" to viewModel.name.value,
                            "weight" to viewModel.weight.value.toInt(),
                            "height" to viewModel.height.value.toInt(),
                            "age" to viewModel.age.value.toInt(),
                            "gender" to if(viewModel.isGenderSelectMan.value) 0 else 1
                        )
                    )
                }.onFailure {
                    it.printStackTrace()
                }
            }
        }
    }
}