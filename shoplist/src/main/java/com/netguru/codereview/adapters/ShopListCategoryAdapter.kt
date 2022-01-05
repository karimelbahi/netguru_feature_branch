package com.netguru.codereview.adapters

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netguru.codereview.shoplist.databinding.ShopListContentItemBinding
import android.widget.ArrayAdapter
import com.netguru.codereview.network.model.ShopListItemResponse


class ShopListCategoryAdapter(val context: Context) :
    ListAdapter<ShopListItemResponse, ShopListCategoryAdapter.ViewHolder>(ShopListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ShopListContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: ShopListContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(shoplist: ShopListItemResponse) {
            binding.apply {
                name.text = shoplist.name
                quantity.text = "${shoplist.quantity}"

            }
        }
    }


    class ShopListDiffCallback : DiffUtil.ItemCallback<ShopListItemResponse>() {

        override fun areItemsTheSame(
            oldItem: ShopListItemResponse,
            newItem: ShopListItemResponse
        ): Boolean {
            return oldItem.itemId === newItem.itemId
        }

        override fun areContentsTheSame(
            oldItem: ShopListItemResponse,
            newItem: ShopListItemResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

}