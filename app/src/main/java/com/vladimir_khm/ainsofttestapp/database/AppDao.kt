package com.vladimir_khm.ainsofttestapp.database

import androidx.room.*
import com.vladimir_khm.ainsofttestapp.model.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AppDao {

    @Query("SELECT * from shop_table")
    abstract fun getAllShops(): Flow<List<Shop>>

    @Query("SELECT * from shop_table WHERE id = :id")
    abstract fun getShopById(id: Int): Flow<Shop>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertShop(shop: Shop)

    @Delete
    abstract suspend fun deleteShop(shop: Shop)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateShop(shop: Shop)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertShopList(shopList: List<Shop>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllShops(shopList: List<Shop>) {
        insertShopList(shopList)
        for (shop in shopList) {
            insertAllStorehouses(shop.storehouses)
        }
    }

    @Transaction
    @Query("SELECT * from shop_table WHERE id = :shopId")
    abstract fun getShopWithStorehouses(shopId: Int): Flow<ShopWithStorehouses>

    @Query("SELECT * from storehouse_table WHERE id = :id")
    abstract fun getStorehouseById(id: Int): Flow<Storehouse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertStorehouse(storehouse: Storehouse)

    @Delete
    abstract suspend fun deleteStorehouse(storehouse: Storehouse)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateStorehouse(storehouse: Storehouse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStorehouseList(storehouseList: List<Storehouse>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStorehouses(storehouseList: List<Storehouse>) {
        insertStorehouseList(storehouseList)
        for (storehouse in storehouseList) {
            insertAllProducts(storehouse.products)
        }
    }

    @Transaction
    @Query("SELECT * from storehouse_table WHERE id = :shopId")
    abstract fun getStorehouseWithProducts(shopId: Int): Flow<StorehouseWithProducts>

    @Query("SELECT * from product_table WHERE id = :id")
    abstract fun getProductById(id: Int): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProduct(product: Product)

    @Delete
    abstract suspend fun deleteProduct(product: Product)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllProducts(productList: List<Product>)
}