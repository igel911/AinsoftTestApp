package com.vladimir_khm.ainsofttestapp.ui.storehouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.model.ShopWithStorehouses
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import com.vladimir_khm.ainsofttestapp.repository.StorehouseRepository
import kotlinx.coroutines.launch

class StorehouseViewModel(
    private val repository: StorehouseRepository
) : ViewModel() {

    lateinit var shopWithStorehouses: LiveData<ShopWithStorehouses>


    fun init(shopId: Int) {
        shopWithStorehouses = repository.getShopWithStorehouses(shopId).asLiveData()
    }

    fun deleteStorehouse(storehouse: Storehouse) = viewModelScope.launch {
        repository.delete(storehouse)
    }
}
