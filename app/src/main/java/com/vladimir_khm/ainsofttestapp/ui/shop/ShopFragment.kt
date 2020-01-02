package com.vladimir_khm.ainsofttestapp.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.util.INVALID_ACTIVITY
import com.vladimir_khm.ainsofttestapp.util.ItemDecorator
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.shop_fragment.*

class ShopFragment : Fragment(R.layout.shop_fragment), Interaction {

    private lateinit var shopAdapter: ShopRvAdapter
    private lateinit var viewModel: ShopViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()

        viewModel = activity?.getViewModel() ?: throw Exception(INVALID_ACTIVITY)

        viewModel.allShops.observe(viewLifecycleOwner, Observer {
            shopAdapter.submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        fab_shop.setOnClickListener {
            val direction = ShopFragmentDirections
                .actionShopFragmentToCreateFragment()
            doNavigate(direction)
        }
    }

    private fun initRecyclerView() {
        rv_shop.apply {
            shopAdapter = ShopRvAdapter(this@ShopFragment)
            adapter = shopAdapter
            addItemDecoration(ItemDecorator(requireContext(), 30))
        }
    }

    private fun doNavigate(direction: NavDirections) {
        view?.findNavController()?.navigate(direction)
    }

    override fun onNameClick(item: Shop) {
        val direction = ShopFragmentDirections
            .actionShopFragmentToStorehouseFragment(item.name, item.id)
        doNavigate(direction)
    }

    override fun onEditClick(item: Shop) {
        val direction = ShopFragmentDirections
            .actionShopFragmentToCreateFragment(item.name, false, item.id)
        doNavigate(direction)
    }

    override fun onDeleteClick(item: Shop) {
        viewModel.deleteShop(item)
    }
}
