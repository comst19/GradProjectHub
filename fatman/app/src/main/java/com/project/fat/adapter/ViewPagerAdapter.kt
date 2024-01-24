package com.project.fat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val fragments: ArrayList<Fragment> = ArrayList()
    // 페이지 갯수 설정
    override fun getItemCount(): Int {
        return fragments.size
    }

    // 불러올 Fragment 정의
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}