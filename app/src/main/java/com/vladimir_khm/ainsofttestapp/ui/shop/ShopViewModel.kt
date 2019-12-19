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
    val allShops: LiveData<List<Shop>>

    init {
        val shopDao = AppDatabase.getInstance(application).appDao()
        repository = ShopRepository(shopDao)
        allShops = repository.allShops
    }

    fun addShop(name: String) = viewModelScope.launch { repository.insert(Shop(name = name)) }

    fun deleteShop(shop: Shop) = viewModelScope.launch { repository.delete(shop) }

    fun updateShop(name: String, id: Int) = viewModelScope.launch {
        repository.update(Shop(id, name))
    }
}
