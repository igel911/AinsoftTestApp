package com.vladimir_khm.ainsofttestapp.ui.product

import androidx.lifecycle.*
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductCreateViewModel(private val repository: ProductRepository) : ViewModel() {

    private val isButtonEnabled = MutableLiveData<Boolean>()
    lateinit var currentProductLD: LiveData<Product>
    private var storehouseId = 0

    fun init(productId: Int, storehouseId: Int) {
        this.storehouseId = storehouseId
        currentProductLD = repository
            .getById(productId)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun isButtonEnabled(): LiveData<Boolean> {
        return isButtonEnabled
    }

    fun checkText(text: String) {
        isButtonEnabled.value = text.trim { it <= ' ' }.isNotEmpty()
    }

    fun saveProduct(name: String) {
        val currentProduct = currentProductLD.value
        if (currentProduct == null) {
            addProduct(name)
        } else {
            updateProduct(name, currentProduct.id)
        }
    }

    private fun addProduct(name: String) = viewModelScope.launch {
        repository.insert(Product(name = name, storehouse_id = storehouseId))
    }

    private fun updateProduct(name: String, id: Int) = viewModelScope.launch {
        repository.update(Product(id, name, storehouseId))
    }
}