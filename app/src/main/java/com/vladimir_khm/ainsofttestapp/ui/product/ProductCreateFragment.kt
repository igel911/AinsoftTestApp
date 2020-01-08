package com.vladimir_khm.ainsofttestapp.ui.product


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.fragment_product_create.*

class ProductCreateFragment : Fragment(R.layout.fragment_product_create) {

    private val args: ProductCreateFragmentArgs by navArgs()
    private lateinit var viewModel: ProductCreateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.getViewModel(args.storehouseId) {
            ProductCreateViewModel(activity?.application!!, args.id, args.storehouseId)
        } ?: return
    }

    override fun onResume() {
        super.onResume()

        et_create_product.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.checkText(charSequence.toString())
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
        viewModel.isButtonEnabled().observe(viewLifecycleOwner, Observer {
            btn_create_product.isEnabled = it
        })
        viewModel.currentProductLD.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                et_create_product.setText(it.name)
                tv_create_product.text = getString(R.string.title, it.name)
            }
        })
        btn_create_product.setOnClickListener {
            viewModel.saveProduct(et_create_product.text.toString())
            view?.clearFocus()
            activity?.onBackPressed()
        }
    }
}
