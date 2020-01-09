package com.vladimir_khm.ainsofttestapp.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.util.ItemDecorator
import kotlinx.android.synthetic.main.product_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProductFragment : Fragment(R.layout.product_fragment), Interaction, KodeinAware {

    private lateinit var storehouseAdapter: ProductRvAdapter
    private val args: ProductFragmentArgs by navArgs()
    override val kodein by kodein()
    private val viewModel: ProductViewModel by instance()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        viewModel.init(args.storehouseId)
        viewModel.storehouseWithProducts.observe(viewLifecycleOwner, Observer {
            storehouseAdapter.submitList(it.products)
        })
    }

    override fun onResume() {
        super.onResume()
        fab_product.setOnClickListener {
            val direction =
                ProductFragmentDirections.actionProductFragmentToProductCreateFragment(args.storehouseId)
            doNavigate(direction)
        }
        tv_title_product.text = args.storehouseName
    }

    private fun initRecyclerView() {
        rv_product.apply {
            storehouseAdapter = ProductRvAdapter(this@ProductFragment)
            adapter = storehouseAdapter
            addItemDecoration(ItemDecorator(requireContext(), 30))
        }
    }

    private fun doNavigate(direction: NavDirections) {
        view?.findNavController()?.navigate(direction)
    }

    override fun onNameClick(item: Product) {}

    override fun onEditClick(item: Product) {
        val direction =
            ProductFragmentDirections.actionProductFragmentToProductCreateFragment(
                args.storehouseId,
                item.id
            )
        doNavigate(direction)
    }

    override fun onDeleteClick(item: Product) {
        viewModel.deleteProduct(item)
    }
}