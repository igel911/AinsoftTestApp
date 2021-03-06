package com.vladimir_khm.ainsofttestapp.repository

import androidx.lifecycle.LiveData
import com.vladimir_khm.ainsofttestapp.database.AppDao
import com.vladimir_khm.ainsofttestapp.model.Shop

class ShopRepository(private val appDao: AppDao) {

    val allShops: LiveData<List<Shop>> = appDao.getAllShops()

    suspend fun insert(shop: Shop) = appDao.insertShop(shop)

    suspend fun delete(shop: Shop) = appDao.deleteShop(shop)

    suspend fun update(shop: Shop) = appDao.updateShop(shop)
}