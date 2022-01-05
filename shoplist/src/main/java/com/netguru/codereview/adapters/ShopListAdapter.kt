package com.netguru.codereview.adapters

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.netguru.codereview.ui.model.ShopList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netguru.codereview.shoplist.databinding.ShopListItemBinding
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.netguru.codereview.utils.setDivider


class ShopListAdapter(val context: Context) :
    ListAdapter<ShopList, ShopListAdapter.ViewHolder>(ShopListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ShopListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: ShopListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val shopListCategoryAdapter = ShopListCategoryAdapter(context)


        fun bind(shoplist: ShopList) {
            binding.apply {
                title.text = shoplist.listName

                recyclerViewCategory.apply {
                    adapter = shopListCategoryAdapter
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                }

                shopListCategoryAdapter.submitList(shoplist.items)


            }
        }
    }


    class ShopListDiffCallback : DiffUtil.ItemCallback<ShopList>() {

        override fun areItemsTheSame(oldItem: ShopList, newItem: ShopList): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopList, newItem: ShopList): Boolean {
            return oldItem == newItem
        }

    }

}