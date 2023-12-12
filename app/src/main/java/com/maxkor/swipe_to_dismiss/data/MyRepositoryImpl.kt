package com.maxkor.swipe_to_dismiss.data

import com.maxkor.swipe_to_dismiss.domain.MyRepository
import com.maxkor.swipe_to_dismiss.presentation.ItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyRepositoryImpl(
    private val dataBase: MyRoomDataBase,
    private val mapper: MyMapper
) : MyRepository {

    override fun getAllItems(): Flow<List<ItemModel>> = dataBase.dao.getAllItems().map {
        mapper.mapEntityListToModelList(it)
    }

    override suspend fun insertItem(itemModel: ItemModel) {
        dataBase.dao.insertItem(mapper.mapModelToEntity(itemModel))
    }

    override suspend fun insertAllItems(items: List<ItemModel>) {
        dataBase.dao.insertAll(mapper.mapModelListToDbEntityList(items))
    }

    override suspend fun insertVarArg(vararg items: MyDbEntity) {
        dataBase.dao.insertVarArg()
    }

    override suspend fun deleteItem(itemModel: ItemModel) {
        dataBase.dao.deleteItem(mapper.mapModelToEntity(itemModel))
    }

    override suspend fun deleteAll() {
       dataBase.dao.deleteAll()
    }
}