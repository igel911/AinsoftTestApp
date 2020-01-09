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
import com.vladimir_khm.ainsofttestapp.ui.product.ProductCreateViewModel
import com.vladimir_khm.ainsofttestapp.ui.product.ProductViewModel
import com.vladimir_khm.ainsofttestapp.ui.shop.ShopCreateViewModel
import com.vladimir_khm.ainsofttestapp.ui.shop.ShopViewModel
import com.vladimir_khm.ainsofttestapp.ui.storehouse.StorehouseCreateViewModel
import com.vladimir_khm.ainsofttestapp.ui.storehouse.StorehouseViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.*

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
        bind() from singleton { ShopRepository(instance()) }
        bind() from singleton { StorehouseRepository(instance()) }
        bind() from singleton { ProductRepository(instance()) }
        bind() from singleton { ShopViewModel(instance()) }
        bind() from singleton { ShopCreateViewModel(instance()) }
        bind() from singleton { StorehouseViewModel(instance()) }
        bind() from singleton { StorehouseCreateViewModel(instance()) }
        bind() from singleton { ProductViewModel(instance()) }
        bind() from singleton { ProductCreateViewModel(instance()) }
    }
}