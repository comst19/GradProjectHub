package com.project.fat.fragment.bottomNavigationActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.fat.R
import com.project.fat.databinding.FragmentRankingBinding
import com.project.fat.adapter.RecyclerviewAdapter
import com.project.fat.adapter.ViewPagerAdapter
import com.project.fat.databinding.FragmentCalendarBinding

class RankingFragment : Fragment() {
    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_ranking, container, false)
        _binding = FragmentRankingBinding.inflate(inflater, container, false)

        val tabTitles = listOf<String>("주간랭킹", "누적랭킹")

        val pagerAdapter = ViewPagerAdapter(requireActivity())
        pagerAdapter.fragments.add(WeekRankingFragment())
        pagerAdapter.fragments.add(TotalRankingFragment())

        binding.viewPager.apply {
            adapter = pagerAdapter
            //setPageTransformer()  //페이지 전환 효과
        }

        TabLayoutMediator(binding.tablayout, binding.viewPager){
                tab, position -> tab.text = tabTitles[position]
            //tab.setIcon(tabIcon[position])  tab마다 아이콘 설정
        }.attach()
//        var listviewAdapter = listviewAdapter(this)
//        rankingBinding.recyclerview.adapter = listviewAdapter
        setTabItemMargin(binding.tablayout, 30)
        return binding.root
    }
    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int = 20) {
        for(i in 0 until 1) {
            val tabs = tabLayout.getChildAt(0) as ViewGroup
            for(i in 0 until tabs.childCount - 1) {
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = marginEnd
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}