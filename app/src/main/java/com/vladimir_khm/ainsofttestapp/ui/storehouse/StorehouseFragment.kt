package com.vladimir_khm.ainsofttestapp.ui.storehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import com.vladimir_khm.ainsofttestapp.util.INVALID_ACTIVITY
import com.vladimir_khm.ainsofttestapp.util.ItemDecorator
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.storehouse_fragment.*

class StorehouseFragment : Fragment(R.layout.storehouse_fragment), Interaction {

    private lateinit var storehouseAdapter: StorehouseRvAdapter
    private val args: StorehouseFragmentArgs by navArgs()
    private lateinit var viewModel: StorehouseViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()

        viewModel = activity?.getViewModel(args.shopId) {
            StorehouseViewModel(activity?.application!!, args.shopId)} ?: throw Exception(INVALID_ACTIVITY)
        println("tag StorehouseFragment ${viewModel.hashCode()} shopId = ${args.shopId}")

        viewModel.shopWithStorehouses.observe(viewLifecycleOwner, Observer {
            storehouseAdapter.submitList(it.storehouses)
        })
    }

    override fun onResume() {
        super.onResume()
        fab_storehouse.setOnClickListener {
            val direction = StorehouseFragmentDirections
                .actionStorehouseFragmentToStorehouseCreateFragment(args.shopId)
            doNavigate(direction)
        }
        tv_title_storehouse.text = args.shopName
    }

    private fun initRecyclerView() {
        rv_storehouse.apply {
            storehouseAdapter = StorehouseRvAdapter(this@StorehouseFragment)
            adapter = storehouseAdapter
            addItemDecoration(ItemDecorator(requireContext(), 30))
        }
    }

    private fun doNavigate(direction: NavDirections) {
        view?.findNavController()?.navigate(direction)
    }

    override fun onNameClick(item: Storehouse) {
        val direction = StorehouseFragmentDirections
            .actionStorehouseFragmentToProductFragment(item.name, item.id)
        doNavigate(direction)
    }

    override fun onEditClick(item: Storehouse) {
        val direction = StorehouseFragmentDirections
            .actionStorehouseFragmentToStorehouseCreateFragment(args.shopId, item.name, false, item.id)
        doNavigate(direction)
    }

    override fun onDeleteClick(item: Storehouse) {
        viewModel.deleteStorehouse(item)
    }
}
