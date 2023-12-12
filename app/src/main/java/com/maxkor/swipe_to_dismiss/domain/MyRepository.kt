package com.maxkor.swipe_to_dismiss.domain

import com.maxkor.swipe_to_dismiss.data.MyDbEntity
import com.maxkor.swipe_to_dismiss.presentation.ItemModel
import kotlinx.coroutines.flow.Flow

interface MyRepository {

    fun getAllItems(): Flow<List<ItemModel>>

    suspend fun insertItem(itemModel: ItemModel)

    suspend fun insertAllItems(items: List<ItemModel>)

    suspend fun insertVarArg(vararg items: MyDbEntity)

    suspend fun deleteItem(itemModel: ItemModel)

    suspend fun deleteAll()

}