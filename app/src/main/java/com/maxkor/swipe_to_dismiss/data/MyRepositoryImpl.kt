package com.maxkor.swipe_to_dismiss.data

import com.maxkor.swipe_to_dismiss.domain.MyRepository
import com.maxkor.swipe_to_dismiss.presentation.ItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class MyRepositoryImpl(
    private val dataBase: MyRoomDataBase,
    private val myMapper: MyMapper
) : MyRepository {

    override fun getAllItems(): Flow<List<ItemModel>> {
        val list = mutableListOf<ItemModel>()
        val entityFlow = dataBase.dao.getAllItems()
        entityFlow.onEach {
            val itemModelList = myMapper.mapEntityListToModelList(it)
            list.addAll(itemModelList)
        }
        return flow {
            list.toList()
        }
    }

    override suspend fun insertItem(itemModel: ItemModel) {
        dataBase.dao.insertItem(myMapper.mapModelToEntity(itemModel))
    }

    override suspend fun deleteItem(itemModel: ItemModel) {
        dataBase.dao.deleteItem(myMapper.mapModelToEntity(itemModel))
    }
}