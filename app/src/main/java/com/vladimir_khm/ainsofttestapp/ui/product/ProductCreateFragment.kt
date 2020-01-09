package com.vladimir_khm.ainsofttestapp.ui.product


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import kotlinx.android.synthetic.main.fragment_product_create.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProductCreateFragment : Fragment(R.layout.fragment_product_create), KodeinAware {

    private val args: ProductCreateFragmentArgs by navArgs()
    override val kodein by kodein()
    private val viewModel: ProductCreateViewModel by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(args.id, args.storehouseId)
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
