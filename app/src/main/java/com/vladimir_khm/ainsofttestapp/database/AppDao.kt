package com.vladimir_khm.ainsofttestapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vladimir_khm.ainsofttestapp.model.*

@Dao
abstract class AppDao {

    @Query("SELECT * from shop_table")
    abstract fun getAllShops(): LiveData<List<Shop>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertShop(shop: Shop)

    @Delete
    abstract suspend fun deleteShop(shop: Shop)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateShop(shop: Shop)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertShopList(shopList: List<Shop>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllShops(shopList: List<Shop>) {
        insertShopList(shopList)
        for (shop in shopList) {
            insertAllStorehouses(shop.storehouses)
        }
    }

    @Transaction
    @Query("SELECT * from shop_table WHERE id = :shopId")
    abstract fun getShopWithStorehouses(shopId: Int): LiveData<ShopWithStorehouses>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertStorehouse(storehouse: Storehouse)

    @Delete
    abstract suspend fun deleteStorehouse(storehouse: Storehouse)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateStorehouse(storehouse: Storehouse)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertStorehouseList(storehouseList: List<Storehouse>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllStorehouses(storehouseList: List<Storehouse>) {
        insertStorehouseList(storehouseList)
        for (storehouse in storehouseList) {
            insertAllProducts(storehouse.products)
        }
    }

    @Transaction
    @Query("SELECT * from storehouse_table WHERE id = :shopId")
    abstract fun getStorehouseWithProducts(shopId: Int): LiveData<StorehouseWithProducts>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertProduct(product: Product)

    @Delete
    abstract suspend fun deleteProduct(product: Product)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAllProducts(productList: List<Product>)
}