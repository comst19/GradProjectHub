package com.project.fat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.project.fat.R

import com.project.fat.data.dto.UserMonster



class GridviewAdapter(val context: Fragment, monsterlist: ArrayList<UserMonster>): RecyclerView.Adapter<GridviewAdapter.ViewHolder>() {
    var list = monsterlist
    private var dataset: ArrayList<List<String>> = arrayListOf<List<String>>().apply {
        for(i in 1..list.size){
            add(listOf("${i}st.")) //recyclerview에 담을 item 임의로 10개 생성
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //recyclerview_item파일의 정보를 Adapter에 붙임
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gridview_item,parent,false)
        //뷰 홀더에 view를 담아서 리턴
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position],list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var items : TextView = itemView.findViewById(R.id.gridTxt)
        private var image: ImageView = itemView.findViewById(R.id.gridImg)
        var i:TextView = itemView.findViewById(R.id.grid)

        fun bind(dataset: List<String>,list: UserMonster){
            i.text = dataset[0]
            items.text = list.name
            Glide.with(context)
                .load(list.imageUrl)
                .into(image)
        }
    }

}