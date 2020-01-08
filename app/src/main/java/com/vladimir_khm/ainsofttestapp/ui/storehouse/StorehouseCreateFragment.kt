package com.vladimir_khm.ainsofttestapp.ui.storehouse


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.fragment_storehouse_create.*

class StorehouseCreateFragment : Fragment(R.layout.fragment_storehouse_create) {

    private val args: StorehouseCreateFragmentArgs by navArgs()
    private lateinit var viewModel: StorehouseCreateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.getViewModel(args.shopId) {
            StorehouseCreateViewModel(activity?.application!!, args.id, args.shopId)
        } ?: return
    }

    override fun onResume() {
        super.onResume()

        et_create_storehouse.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.checkText(charSequence.toString())
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
        viewModel.isButtonEnabled().observe(viewLifecycleOwner, Observer {
            btn_create_storehouse.isEnabled = it
        })
        viewModel.currentStorehouseLD.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                et_create_storehouse.setText(it.name)
                tv_create_storehouse.text = getString(R.string.title, it.name)
            }
        })
        btn_create_storehouse.setOnClickListener {
            viewModel.saveStorehouse(et_create_storehouse.text.toString())
            view?.clearFocus()
            activity?.onBackPressed()
        }
    }
}
