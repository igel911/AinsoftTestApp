package com.vladimir_khm.ainsofttestapp.model

import androidx.room.*

@Entity(
    tableName = "product_table", foreignKeys = [ForeignKey(
        entity = Storehouse::class,
        parentColumns = ["id"],
        childColumns = ["storehouse_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("storehouse_id")]
)
data class Product(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "storehouse_id") val storehouse_id: Int
)
