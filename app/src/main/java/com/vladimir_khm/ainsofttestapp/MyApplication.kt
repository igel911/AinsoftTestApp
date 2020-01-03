package com.vladimir_khm.ainsofttestapp

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.vladimir_khm.ainsofttestapp.database.AppDao
import com.vladimir_khm.ainsofttestapp.database.AppDatabase
import com.vladimir_khm.ainsofttestapp.database.SeedDatabaseWorker
import com.vladimir_khm.ainsofttestapp.repository.ProductRepository
import com.vladimir_khm.ainsofttestapp.repository.ShopRepository
import com.vladimir_khm.ainsofttestapp.repository.StorehouseRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with

class MyApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        constant(tag = "DATABASE_NAME") with "shop_database"
        constant(tag = "SHOP_DATA_FILENAME") with "shops.json"

        bind<AppDatabase>() with singleton {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                instance("DATABASE_NAME")
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(applicationContext).enqueue(request)
                    }
                })
                .build()
        }
        bind<AppDao>() with singleton { instance<AppDatabase>().appDao() }
        bind<ShopRepository>() with singleton { ShopRepository(instance()) }
        bind<StorehouseRepository>() with singleton { StorehouseRepository(instance()) }
        bind<ProductRepository>() with singleton { ProductRepository(instance()) }
    }
}