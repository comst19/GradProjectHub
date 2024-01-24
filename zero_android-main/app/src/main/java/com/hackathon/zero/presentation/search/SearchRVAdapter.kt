package com.hackathon.zero.presentation.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hackathon.zero.data.Product
import com.hackathon.zero.data.ProductItem
import com.hackathon.zero.data.ProductSearchItem
import com.hackathon.zero.databinding.ItemCalorieCardBinding
import com.hackathon.zero.databinding.ItemProductSearchBinding

class SearchRVAdapter(val onSelectClicked: (Int) -> Unit) :
    ListAdapter<ProductSearchItem, SearchRVAdapter.ProductItemHolder>(diffutil) {

    companion object {
        val diffutil = object: DiffUtil.ItemCallback<ProductSearchItem>() {
            override fun areItemsTheSame(
                oldItem: ProductSearchItem,
                newItem: ProductSearchItem
            ): Boolean {
                Log.e("아1", (oldItem.product.productName == newItem.product.productName).toString())
                return oldItem.product.productName == newItem.product.productName
            }

            override fun areContentsTheSame(
                oldItem: ProductSearchItem,
                newItem: ProductSearchItem
            ): Boolean {
                Log.e("아2", (oldItem.isSelect == newItem.isSelect).toString())
                return oldItem.isSelect == newItem.isSelect
            }

        }
    }
    inner class ProductItemHolder(
        private val binding: ItemProductSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductSearchItem) {
            binding.item = data
            binding.root.setOnClickListener {
                onSelectClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder =
        ProductItemHolder(
            ItemProductSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}