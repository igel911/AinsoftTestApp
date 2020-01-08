package com.vladimir_khm.ainsofttestapp.repository

import com.vladimir_khm.ainsofttestapp.database.AppDao
import com.vladimir_khm.ainsofttestapp.model.Product

class ProductRepository(private val appDao: AppDao) {

    fun getStorehouseWithProducts(shopId: Int) = appDao.getStorehouseWithProducts(shopId)

    fun getById(id: Int) = appDao.getProductById(id)

    suspend fun insert(product: Product) = appDao.insertProduct(product)

    suspend fun delete(product: Product) = appDao.deleteProduct(product)

    suspend fun update(product: Product) = appDao.updateProduct(product)
}