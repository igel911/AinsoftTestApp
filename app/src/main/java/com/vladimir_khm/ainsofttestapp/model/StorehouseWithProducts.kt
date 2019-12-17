package com.vladimir_khm.ainsofttestapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class StorehouseWithProducts(
    @Embedded val shop: Storehouse,
    @Relation(parentColumn = "id", entity = Product::class, entityColumn = "storehouse_id")
    val products: List<Product>)
