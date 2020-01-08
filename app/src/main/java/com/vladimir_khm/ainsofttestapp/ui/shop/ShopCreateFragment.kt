package com.vladimir_khm.ainsofttestapp.ui.shop


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.fragment_shop_create.*

class ShopCreateFragment : Fragment(R.layout.fragment_shop_create) {

    private val args: ShopCreateFragmentArgs by navArgs()
    private lateinit var viewModel: ShopCreateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.getViewModel(args.id) {
            ShopCreateViewModel(activity!!.application, args.id)
        } ?: return
    }

    override fun onResume() {
        super.onResume()

        et_create_shop.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.checkText(charSequence.toString())
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })

        viewModel.isButtonEnabled().observe(viewLifecycleOwner, Observer {
            btn_create_shop.isEnabled = it
        })
        viewModel.currentShopLD.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                et_create_shop.setText(it.name)
                tv_create_shop.text = getString(R.string.title, it.name)
            }
        })
        btn_create_shop.setOnClickListener {
            viewModel.saveShop(et_create_shop.text.toString())
            view?.clearFocus()
            activity?.onBackPressed()
        }
    }
}
