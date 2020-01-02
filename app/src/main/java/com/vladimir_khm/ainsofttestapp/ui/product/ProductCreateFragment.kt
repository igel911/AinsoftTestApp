package com.vladimir_khm.ainsofttestapp.ui.product


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.util.INVALID_ACTIVITY
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.fragment_product_create.*

class ProductCreateFragment : Fragment(R.layout.fragment_product_create) {

    private val args: ProductCreateFragmentArgs by navArgs()
    private lateinit var viewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.getViewModel(args.storehouseId) {
            ProductViewModel(activity?.application!!, args.storehouseId)
        } ?: throw Exception(INVALID_ACTIVITY)
    }

    override fun onResume() {
        super.onResume()

        et_create_product.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                btn_create_product.isEnabled = charSequence
                    .toString()
                    .trim { it <= ' ' }
                    .isNotEmpty()
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
        val isNew = args.isNew
        btn_create_product.setOnClickListener {
            if (isNew) {
                viewModel.addProduct(et_create_product.text.toString())
            } else {
                viewModel.updateProduct(et_create_product.text.toString(), args.id)
            }
            view?.clearFocus()
            activity?.onBackPressed()
        }
        if (!isNew) {
            val title = args.title
            et_create_product.setText(title)
            tv_create_product.text = getString(R.string.title, title)
        }
    }


}
