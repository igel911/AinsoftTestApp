package com.vladimir_khm.ainsofttestapp.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "storehouse_table", foreignKeys = [ForeignKey(
        entity = Shop::class,
        parentColumns = ["id"],
        childColumns = ["shop_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )],
    indices = [Index("shop_id")]
)
data class Storehouse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "shop_id") val shop_id: Int
) {
    @Ignore val products: List<Product> = emptyList()
}