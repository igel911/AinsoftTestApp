package com.vladimir_khm.ainsofttestapp.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.model.StorehouseWithProducts
import com.vladimir_khm.ainsofttestapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    lateinit var storehouseWithProducts: LiveData<StorehouseWithProducts>


    fun init(storehouseId: Int) {
        storehouseWithProducts = repository
            .getStorehouseWithProducts(storehouseId)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun deleteProduct(shop: Product) = viewModelScope.launch { repository.delete(shop) }
}
