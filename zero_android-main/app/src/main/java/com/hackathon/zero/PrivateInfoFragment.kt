package com.hackathon.zero

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hackathon.zero.databinding.FragmentPrivateInfoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PrivateInfoFragment : Fragment() {

    lateinit var binding: FragmentPrivateInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentPrivateInfoBinding.inflate(inflater, container, false)
        .also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agreeBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_privateInfoFragment_to_inputInfoFragment
            )
        }
    }
}