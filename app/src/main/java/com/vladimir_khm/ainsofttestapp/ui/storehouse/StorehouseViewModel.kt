package com.vladimir_khm.ainsofttestapp.ui.storehouse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir_khm.ainsofttestapp.model.Storehouse
import com.vladimir_khm.ainsofttestapp.repository.StorehouseRepository
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class StorehouseViewModel(
    application: Application,
    shopId: Int
) : AndroidViewModel(application), KodeinAware {

    override val kodein by kodein(application)
    private val repository: StorehouseRepository by instance()
    val shopWithStorehouses = repository.getShopWithStorehouses(shopId).asLiveData()
    

    fun deleteStorehouse(storehouse: Storehouse) = viewModelScope.launch {
        repository.delete(storehouse)
    }
}
