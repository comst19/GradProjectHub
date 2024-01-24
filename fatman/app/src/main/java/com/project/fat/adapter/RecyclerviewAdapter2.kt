package com.project.fat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.fat.R
import com.project.fat.data.dto.TotalRankResponse
import com.project.fat.data.dto.TotalRankResponseModel


class RecyclerviewAdapter2(list: TotalRankResponseModel) : RecyclerView.Adapter<RecyclerviewAdapter2.ViewHolder>() {
    private var dataset: ArrayList<List<String>> = arrayListOf<List<String>>().apply {
        for(i in 1..list.size){
            add(listOf("${i}st.")) //recyclerview에 담을 item 임의로 10개 생성
        }
    }
    var ranklist = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter2.ViewHolder {
        //recyclerview_item파일의 정보를 Adapter에 붙임
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false)
        //뷰 홀더에 view를 담아서 리턴
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerviewAdapter2.ViewHolder, position: Int) {
        holder.bind(dataset[position], ranklist[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var rank: TextView = itemView.findViewById(R.id.nst_nickname)
        private var nickname: TextView = itemView.findViewById(R.id.nickname)
        private var fats: TextView = itemView.findViewById(R.id.howManyFats)
        private var distance: TextView = itemView.findViewById(R.id.howLongDst)

        fun bind(ranking: List<String>, list: TotalRankResponse) {
            rank.text = ranking[0]
            nickname.text = list.user.nickname
            fats.text = list.monsterNum.toString()
            distance.text = list.distance.toString()
        }
    }
}