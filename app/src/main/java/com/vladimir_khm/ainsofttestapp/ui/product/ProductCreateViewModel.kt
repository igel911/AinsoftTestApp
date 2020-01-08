package com.vladimir_khm.ainsofttestapp.ui.product

import android.app.Application
import androidx.lifecycle.*
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.repository.ProductRepository
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProductCreateViewModel(
    application: Application,
    productId: Int,
    private val storehouseId: Int
) : AndroidViewModel(application), KodeinAware {

    override val kodein by kodein(application)
    private val repository: ProductRepository by instance()
    private val isButtonEnabled = MutableLiveData<Boolean>()
    val currentProductLD = repository
        .getById(productId)
        .asLiveData(viewModelScope.coroutineContext)

    fun isButtonEnabled(): LiveData<Boolean> {
        return isButtonEnabled
    }

    fun checkText(text: String) {
        isButtonEnabled.value = text.trim { it <= ' ' }.isNotEmpty()
    }

    fun saveProduct(name: String) {
        val currentStorehouse = currentProductLD.value
        if (currentStorehouse == null) {
            addProduct(name)
        } else {
            updateProduct(name, currentStorehouse.id)
        }
    }

    private fun addProduct(name: String) = viewModelScope.launch {
        repository.insert(Product(name = name, storehouse_id = storehouseId))
    }

    private fun updateProduct(name: String, id: Int) = viewModelScope.launch {
        repository.update(Product(id, name, storehouseId))
    }
}