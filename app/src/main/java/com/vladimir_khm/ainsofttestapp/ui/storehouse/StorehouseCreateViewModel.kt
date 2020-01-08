package com.vladimir_khm.ainsofttestapp.ui.storehouse

import android.app.Application
import androidx.lifecycle.*
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import com.vladimir_khm.ainsofttestapp.repository.StorehouseRepository
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class StorehouseCreateViewModel
    (application: Application,
     storehouseId: Int,
     private val shopId: Int
) : AndroidViewModel(application), KodeinAware {

    override val kodein by kodein(application)
    private val repository: StorehouseRepository by instance()
    private val isButtonEnabled = MutableLiveData<Boolean>()
    val currentStorehouseLD = repository
        .getById(storehouseId)
        .asLiveData(viewModelScope.coroutineContext)

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