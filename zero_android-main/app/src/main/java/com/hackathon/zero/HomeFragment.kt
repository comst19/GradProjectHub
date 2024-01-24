package com.hackathon.zero

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.hackathon.zero.base.BaseFragment
import com.hackathon.zero.base.HomeViewModelImpl
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackathon.zero.databinding.FragmentHomeBinding
import com.hackathon.zero.presentation.search.SearchRVAdapter
import com.hackathon.zero.util.Constants.USER_ID
import com.hackathon.zero.util.SharedPreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private lateinit var sp: SharedPreferencesUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.llTotalIntake.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_myFragment
            )
        }
        val listener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) viewModel.queryChanged()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.query.value = newText?:""
                return true
            }
        }
        binding.searchView.setOnQueryTextListener(listener)

        sp = SharedPreferencesUtil(requireActivity())


        binding.btnStart.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_privateInfoFragment)
        }

        binding.editGoalBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_goalSettingFragment)
        }


    }

    override fun onResume() {
        super.onResume()
        if (sp.getInt(USER_ID, HomeViewModelImpl.USER_ID_NONE) != HomeViewModelImpl.USER_ID_NONE) {
            binding.btnStart.visibility = View.INVISIBLE
        }

        val recyclerView = binding.rvSearchResult

        viewModel.productList.observe(requireActivity()) {
            val layoutManager = LinearLayoutManager(requireContext())
            if(recyclerView.adapter == null) {
                recyclerView.adapter = SearchRVAdapter() { index ->
                    viewModel.listUpdate(index)
                }
                recyclerView.layoutManager = layoutManager
            }
            (recyclerView.adapter as SearchRVAdapter).submitList(it)
        }

    }
}