package com.maxkor.swipe_to_dismiss.data

import com.maxkor.swipe_to_dismiss.presentation.ItemModel

class MyMapper {

    fun mapModelToEntity(itemModel: ItemModel): MyDbEntity {
        return MyDbEntity(
            dbOrderId = itemModel.orderId,
            id = itemModel.id,
            imageResId = itemModel.imageResId,
            text = itemModel.text,
            isChecked = itemModel.isChecked
        )
    }

    fun mapEntityToModel(myDbEntity: MyDbEntity): ItemModel {
        return ItemModel(
            orderId = myDbEntity.dbOrderId,
            id = myDbEntity.id,
            imageResId = myDbEntity.imageResId,
            text = myDbEntity.text,
            isChecked = myDbEntity.isChecked
        )
    }

    fun mapEntityListToModelList(list: List<MyDbEntity>): List<ItemModel> {
        return list.map { mapEntityToModel(it) }
    }

    fun mapModelListToDbEntityList(list: List<ItemModel>): List<MyDbEntity> {
        return list.map { mapModelToEntity(it) }
    }

}