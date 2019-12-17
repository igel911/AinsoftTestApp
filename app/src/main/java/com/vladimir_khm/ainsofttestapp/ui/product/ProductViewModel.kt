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

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: ProductRepository
    private lateinit var storehouseWithProduct: LiveData<StorehouseWithProducts>
    private var storehouseId = 0


    init {
        val appDao = AppDatabase.getInstance(application).appDao()
        repository = ProductRepository(appDao)
    }

    fun initVm(id: Int) {
        storehouseId = id
        storehouseWithProduct = repository.getStorehouseWithProducts(id)
    }

    fun addProduct(name: String) = viewModelScope.launch {
        val shop = Product(name = name, storehouse_id = storehouseId)
        repository.insert(shop)
    }

    fun deleteProduct(shop: Product) = viewModelScope.launch {
        repository.delete(shop)
    }

    fun updateProduct(name: String, id: Int) = viewModelScope.launch {
        val shop = Product(id, name, storehouseId)
        repository.update(shop)
    }

    fun getStorehouseWithProducts(): LiveData<StorehouseWithProducts> = storehouseWithProduct
}
