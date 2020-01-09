package com.vladimir_khm.ainsofttestapp.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.util.ItemDecorator
import kotlinx.android.synthetic.main.shop_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ShopFragment : Fragment(R.layout.shop_fragment), Interaction, KodeinAware {

    override val kodein by kodein()
    private lateinit var shopAdapter: ShopRvAdapter
    private val viewModel: ShopViewModel by instance()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()

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
            .actionShopFragmentToCreateFragment(item.id)
        doNavigate(direction)
    }

    override fun onDeleteClick(item: Shop) {
        viewModel.deleteShop(item)
    }
}
