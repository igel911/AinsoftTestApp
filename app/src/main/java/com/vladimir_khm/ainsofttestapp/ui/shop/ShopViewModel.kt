package com.vladimir_khm.ainsofttestapp.ui.shop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.database.AppDatabase
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.repository.ShopRepository
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShopRepository
    private val allShops: LiveData<List<Shop>>

    init {
        val shopDao = AppDatabase.getInstance(application).appDao()
        repository = ShopRepository(shopDao)
        allShops = repository.allShops
    }

    fun addShop(name: String) = viewModelScope.launch {
        val shop = Shop(name = name)
        repository.insert(shop)
    }

    fun deleteShop(shop: Shop) = viewModelScope.launch {
        repository.delete(shop)
    }

    fun updateShop(name: String, id: Int) = viewModelScope.launch {
        val shop = Shop(id, name)
        repository.update(shop)
    }

    fun getShops(): LiveData<List<Shop>> {
        return allShops
    }
}
