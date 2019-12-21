package com.vladimir_khm.ainsofttestapp.ui.storehouse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.database.AppDatabase
import com.vladimir_khm.ainsofttestapp.model.ShopWithStorehouses
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import com.vladimir_khm.ainsofttestapp.repository.StorehouseRepository
import kotlinx.coroutines.launch

class StorehouseViewModel(application: Application, private val shopId: Int) : AndroidViewModel(application) {

    private val repository: StorehouseRepository
    val shopWithStorehouses: LiveData<ShopWithStorehouses>


    init {
        val appDao = AppDatabase.getInstance(application).appDao()
        repository = StorehouseRepository(appDao)
        shopWithStorehouses = repository.getShopWithStorehouses(shopId)
        println("tag StorehouseViewModel ${this@StorehouseViewModel.hashCode()} shopId = $shopId")
    }

    fun addStorehouse(name: String) = viewModelScope.launch {
        repository.insert(Storehouse(name = name, shop_id = shopId))
    }

    fun deleteStorehouse(storehouse: Storehouse) = viewModelScope.launch {
        repository.delete(storehouse)
    }

    fun updateStorehouse(name: String, id: Int) = viewModelScope.launch {
        repository.update(Storehouse(id, name, shopId))
    }
}
