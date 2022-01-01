package com.netguru.codereview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.netguru.codereview.adapters.ShopListAdapter
import com.netguru.codereview.network.model.ShopListItemResponse
import com.netguru.codereview.network.model.ShopListResponse
import com.netguru.codereview.shoplist.R
import com.netguru.codereview.shoplist.databinding.MainFragmentBinding
import com.netguru.codereview.shoplist.databinding.ShopListItemBinding
import com.netguru.codereview.ui.model.ShopList
import javax.inject.Inject


class MainFragment : Fragment(R.layout.main_fragment) {


    // inject by constructor
    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MainFragmentBinding.bind(view)


        val shopListAdapter = ShopListAdapter(requireActivity())

        binding.apply {
            progressCircular.visibility = View.VISIBLE

            recyclerViewTasks.apply {
                adapter = shopListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

        }

        viewModel.shopLists.observe(viewLifecycleOwner, { lists ->
            val progressBar = view.findViewById<ProgressBar>(R.id.message)
            val latestIcon = view.findViewById<ImageView>(R.id.latest_list_icon)

            val shopLists = lists.map { mapShopList(it.first, it.second) }.also {
                latestIcon?.load(it.first().iconUrl)
            }

            binding.progressCircular.visibility = View.GONE
            shopListAdapter.submitList(shopLists)

        })
        viewModel.events.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun mapShopList(list: ShopListResponse, items: List<ShopListItemResponse>) =
        ShopList(
            list.list_id,
            list.userId,
            list.listName,
            list.listName,
            items
        )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
