package com.vladimir_khm.ainsofttestapp.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.database.AppDatabase
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.model.StorehouseWithProducts
import com.vladimir_khm.ainsofttestapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application, private val storehouseId: Int) : AndroidViewModel(application) {

    private val repository: ProductRepository
    val storehouseWithProducts: LiveData<StorehouseWithProducts>


    init {
        val appDao = AppDatabase.getInstance(application).appDao()
        repository = ProductRepository(appDao)
        storehouseWithProducts = repository.getStorehouseWithProducts(storehouseId)
        println("tag ProductViewModel ${this@ProductViewModel.hashCode()} storehouseId = $storehouseId")
    }

    fun addProduct(name: String) = viewModelScope.launch {
        repository.insert(Product(name = name, storehouse_id = storehouseId))
    }

    fun deleteProduct(shop: Product) = viewModelScope.launch { repository.delete(shop) }

    fun updateProduct(name: String, id: Int) = viewModelScope.launch {
        repository.update(Product(id, name, storehouseId))
    }
}
