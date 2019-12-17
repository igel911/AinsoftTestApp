package com.vladimir_khm.ainsofttestapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class ShopWithStorehouses(
    @Embedded val shop: Shop,
    @Relation(parentColumn = "id", entity = Storehouse::class, entityColumn = "shop_id")
    val storehouses: List<Storehouse>
)