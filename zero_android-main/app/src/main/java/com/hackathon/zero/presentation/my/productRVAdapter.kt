package com.hackathon.zero.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackathon.zero.data.Product
import com.hackathon.zero.databinding.ItemCalorieCardBinding

class productRVAdapter() :
    RecyclerView.Adapter<productRVAdapter.ProductItemHolder>() {

    private var list: List<Product> = listOf()

    inner class ProductItemHolder(
        private val binding: ItemCalorieCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Product, ) {
            binding.item = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder =
        ProductItemHolder(
            ItemCalorieCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(newList: List<Product>) {
        list = newList
        notifyDataSetChanged()
    }
}