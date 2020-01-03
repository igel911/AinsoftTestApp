/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vladimir_khm.ainsofttestapp.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.vladimir_khm.ainsofttestapp.model.Shop
import kotlinx.coroutines.coroutineScope
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KodeinAware {

    override val kodein by kodein(context.applicationContext)
    private val fileName: String by instance("SHOP_DATA_FILENAME")

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(fileName).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val database: AppDatabase by instance()

                    val shopType = object : TypeToken<List<Shop>>() {}.type
                    val shopList: List<Shop> = Gson().fromJson(jsonReader, shopType)

                    database.appDao().insertAllShops(shopList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}