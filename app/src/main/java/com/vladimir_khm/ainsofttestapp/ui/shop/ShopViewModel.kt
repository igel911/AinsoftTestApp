package com.vladimir_khm.ainsofttestapp.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.repository.ShopRepository
import kotlinx.coroutines.launch


class ShopViewModel(val repository: ShopRepository) : ViewModel() {

    val allShops = repository.getAll().asLiveData(viewModelScope.coroutineContext)


    fun deleteShop(shop: Shop) = viewModelScope.launch { repository.delete(shop) }
}
