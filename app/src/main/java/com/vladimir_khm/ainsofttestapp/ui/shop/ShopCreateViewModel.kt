package com.vladimir_khm.ainsofttestapp.ui.shop

import android.app.Application
import androidx.lifecycle.*
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.repository.ShopRepository
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShopCreateViewModel(
    application: Application,
    shopId: Int
    ) : AndroidViewModel(application), KodeinAware {

    override val kodein by kodein(application)
    private val repository: ShopRepository by instance()
    private val isButtonEnabled = MutableLiveData<Boolean>()
     val currentShopLD = repository
        .getById(shopId)
        .asLiveData(viewModelScope.coroutineContext)

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