package com.vladimir_khm.ainsofttestapp.ui.storehouse

import androidx.lifecycle.*
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import com.vladimir_khm.ainsofttestapp.repository.StorehouseRepository
import kotlinx.coroutines.launch

class StorehouseCreateViewModel(private val repository: StorehouseRepository) : ViewModel() {

    private val isButtonEnabled = MutableLiveData<Boolean>()
    lateinit var currentStorehouseLD: LiveData<Storehouse>
    private var shopId = 0

    fun init(storehouseId: Int, shopId: Int) {
        this.shopId = shopId
        currentStorehouseLD = repository
            .getById(storehouseId)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun isButtonEnabled(): LiveData<Boolean> {
        return isButtonEnabled
    }

    fun checkText(text: String) {
        isButtonEnabled.value = text.trim { it <= ' ' }.isNotEmpty()
    }

    fun saveStorehouse(name: String) {
        val currentStorehouse = currentStorehouseLD.value
        if (currentStorehouse == null) {
            addStorehouse(name)
        } else {
            updateStorehouse(name, currentStorehouse.id)
        }
    }

    private fun addStorehouse(name: String) = viewModelScope.launch {
        repository.insert(Storehouse(name = name, shop_id = shopId))
    }

    private fun updateStorehouse(name: String, id: Int) = viewModelScope.launch {
        repository.update(Storehouse(id, name, shopId))
    }
}