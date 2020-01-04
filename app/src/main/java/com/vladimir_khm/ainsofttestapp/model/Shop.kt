package com.vladimir_khm.ainsofttestapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "shop_table")
data class Shop(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String
) {
    @Ignore
    val storehouses: List<Storehouse> = emptyList()
}
