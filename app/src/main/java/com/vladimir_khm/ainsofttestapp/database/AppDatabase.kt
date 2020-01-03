package com.vladimir_khm.ainsofttestapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladimir_khm.ainsofttestapp.model.Product
import com.vladimir_khm.ainsofttestapp.model.Shop
import com.vladimir_khm.ainsofttestapp.model.Storehouse

@Database(
    entities = [Shop::class, Storehouse::class, Product::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}

