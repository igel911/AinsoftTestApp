package com.vladimir_khm.ainsofttestapp.repository

import com.vladimir_khm.ainsofttestapp.database.AppDao
import com.vladimir_khm.ainsofttestapp.model.Storehouse

class StorehouseRepository(private val appDao: AppDao) {

    fun getShopWithStorehouses(shopId: Int) = appDao.getShopWithStorehouses(shopId)

    fun getById(id: Int) = appDao.getStorehouseById(id)

    suspend fun insert(storehouse: Storehouse) = appDao.insertStorehouse(storehouse)

    suspend fun delete(storehouse: Storehouse) = appDao.deleteStorehouse(storehouse)

    suspend fun update(storehouse: Storehouse) = appDao.updateStorehouse(storehouse)
}