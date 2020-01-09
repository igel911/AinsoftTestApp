package com.vladimir_khm.ainsofttestapp.ui.shop

import androidx.lifecycle.*
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.repository.ShopRepository
import kotlinx.coroutines.launch

class ShopCreateViewModel(val repository: ShopRepository) : ViewModel() {

    private val isButtonEnabled = MutableLiveData<Boolean>()
    lateinit var currentShopLD: LiveData<Shop>

    fun init(shopId: Int) {
        currentShopLD = repository
            .getById(shopId)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun isButtonEnabled(): LiveData<Boolean> {
        return isButtonEnabled
    }

    fun checkText(text: String) {
        isButtonEnabled.value = text.trim { it <= ' ' }.isNotEmpty()
    }

    fun saveShop(name: String) {
        val currentShop = currentShopLD.value
        if (currentShop == null) {
            addShop(name)
        } else {
            updateShop(name, currentShop.id)
        }
    }

    private fun addShop(name: String) = viewModelScope.launch {
        repository.insert(Shop(name = name))
    }

    private fun updateShop(name: String, id: Int) = viewModelScope.launch {
        repository.update(Shop(id, name))
    }
}