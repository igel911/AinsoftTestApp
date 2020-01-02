package com.vladimir_khm.ainsofttestapp.ui.storehouse


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vladimir_khm.ainsofttestapp.R
import com.vladimir_khm.ainsofttestapp.util.INVALID_ACTIVITY
import com.vladimir_khm.ainsofttestapp.util.getViewModel
import kotlinx.android.synthetic.main.fragment_storehouse_create.*

class StorehouseCreateFragment : Fragment(R.layout.fragment_storehouse_create) {

    private val args: StorehouseCreateFragmentArgs by navArgs()
    private lateinit var viewModel: StorehouseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.getViewModel(args.shopId) {
            StorehouseViewModel(activity?.application!!, args.shopId)
        } ?: throw Exception(INVALID_ACTIVITY)
    }

    override fun onResume() {
        super.onResume()

        et_create_storehouse.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                btn_create_storehouse.isEnabled = charSequence
                    .toString()
                    .trim { it <= ' ' }
                    .isNotEmpty()
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
        val isNew = args.isNew
        btn_create_storehouse.setOnClickListener {
            if (isNew) {
                viewModel.addStorehouse(et_create_storehouse.text.toString())
            } else {
                viewModel.updateStorehouse(et_create_storehouse.text.toString(), args.id)
            }
            view?.clearFocus()
            activity?.onBackPressed()
        }
        if (!isNew) {
            val title = args.title
            et_create_storehouse.setText(title)
            tv_create_storehouse.text = getString(R.string.title, title)
        }
    }
}
