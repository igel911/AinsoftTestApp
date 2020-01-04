package com.vladimir_khm.ainsofttestapp.ui.shop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShopViewModel(application: Application) : AndroidViewModel(application), KodeinAware {

    override val kodein by kodein(application)
    private val repository: ShopRepository by instance()
    val allShops = repository.allShops().asLiveData()


    fun addShop(name: String) = viewModelScope.launch { repository.insert(Shop(name = name)) }

    fun deleteShop(shop: Shop) = viewModelScope.launch { repository.delete(shop) }

    fun updateShop(name: String, id: Int) = viewModelScope.launch {
        repository.update(Shop(id, name))
    }
}
