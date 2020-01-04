package com.vladimir_khm.ainsofttestapp.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.repository.ProductRepository
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProductViewModel(
    application: Application,
    private val storehouseId: Int
) : AndroidViewModel(application), KodeinAware {

    override val kodein by kodein(application)
    private val repository: ProductRepository by instance()
    val storehouseWithProducts = repository.getStorehouseWithProducts(storehouseId).asLiveData()


    fun addProduct(name: String) = viewModelScope.launch {
        repository.insert(Product(name = name, storehouse_id = storehouseId))
    }

    fun deleteProduct(shop: Product) = viewModelScope.launch { repository.delete(shop) }

    fun updateProduct(name: String, id: Int) = viewModelScope.launch {
        repository.update(Product(id, name, storehouseId))
    }
}
