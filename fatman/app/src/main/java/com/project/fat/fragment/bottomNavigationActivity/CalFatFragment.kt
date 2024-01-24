package com.project.fat.fragment.bottomNavigationActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.fat.R
import com.project.fat.adapter.ViewPagerAdapter
import com.project.fat.databinding.FragmentCalFatBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalFatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalFatFragment : Fragment() {
    private lateinit var binding: FragmentCalFatBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalFatBinding.inflate(layoutInflater)

        val tabTitles = listOf<String>("history", "도감")

        val pagerAdapter = ViewPagerAdapter(requireActivity())
        pagerAdapter.fragments.add(CalendarFragment())
        pagerAdapter.fragments.add(FatbookFragment())

        binding.viewPager.apply {
            adapter = pagerAdapter
            //setPageTransformer()  //페이지 전환 효과
        }

        TabLayoutMediator(binding.tablayout, binding.viewPager){
                tab, position -> tab.text = tabTitles[position]
            //tab.setIcon(tabIcon[position])  tab마다 아이콘 설정
        }.attach()

        setTabItemMargin(binding.tablayout, 30)
        return binding.root
    }

    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int = 20) {

        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for(i in 0 until tabs.childCount - 1) {
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            lp.marginEnd = marginEnd
            tab.layoutParams = lp
            tabLayout.requestLayout()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalFatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalFatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}