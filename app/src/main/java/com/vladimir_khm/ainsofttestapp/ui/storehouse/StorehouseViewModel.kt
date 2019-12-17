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

class StorehouseViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: StorehouseRepository
    private lateinit var shopWithStorehouses: LiveData<ShopWithStorehouses>
    private var shopId = 0


    init {
        val appDao = AppDatabase.getInstance(application).appDao()
        repository = StorehouseRepository(appDao)
    }

    fun initVm(id: Int) {
        shopId = id
        shopWithStorehouses = repository.getShopWithStorehouses(id)
    }

    fun addStorehouse(name: String) = viewModelScope.launch {
        val storehouse = Storehouse(name = name, shop_id = shopId)
        repository.insert(storehouse)
    }

    fun deleteStorehouse(storehouse: Storehouse) = viewModelScope.launch {
        repository.delete(storehouse)
    }

    fun updateStorehouse(name: String, id: Int) = viewModelScope.launch {
        val storehouse = Storehouse(id, name, shopId)
        repository.update(storehouse)
    }

    fun getShopWithStorehouses(): LiveData<ShopWithStorehouses> = shopWithStorehouses
}
