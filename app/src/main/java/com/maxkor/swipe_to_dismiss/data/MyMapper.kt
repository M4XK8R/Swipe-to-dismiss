package com.maxkor.swipe_to_dismiss.data

import com.maxkor.swipe_to_dismiss.presentation.ItemModel

class MyMapper {

    fun mapModelToEntity(itemModel: ItemModel): MyEntity {
        return MyEntity(
            id = itemModel.id,
            imageResId = itemModel.imageResId,
            text = itemModel.text,
            isChecked = itemModel.isChecked
        )
    }

    private fun mapEntityToModel(myEntity: MyEntity): ItemModel {
        return ItemModel(
            id = myEntity.id,
            imageResId = myEntity.imageResId,
            text = myEntity.text,
            isChecked = myEntity.isChecked
        )
    }

    fun mapEntityListToModelList(list: List<MyEntity>): List<ItemModel> {
        return list.map { mapEntityToModel(it) }
    }
}