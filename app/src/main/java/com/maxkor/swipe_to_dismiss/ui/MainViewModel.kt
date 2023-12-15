package com.maxkor.swipe_to_dismiss.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.maxkor.swipe_to_dismiss.domain.ItemModel
import org.burnoutcrew.reorderable.ItemPosition

private const val LIST_SIZE = 20

class MainViewModel : ViewModel() {
    private val initialData = List(LIST_SIZE) { ItemModel(id = it) }

    private val _itemsState = mutableStateOf(initialData)
    val itemsState: State<List<ItemModel>> = _itemsState

    fun swapElements(
        itemPositionFrom: ItemPosition,
        itemPositionTo: ItemPosition
    ) {
        _itemsState.value = _itemsState.value.toMutableList().apply {
            add(itemPositionTo.index, removeAt(itemPositionFrom.index))
        }
    }

    fun removeItem(item: ItemModel) {
        val currentList = _itemsState.value.toMutableList()
        currentList.remove(item)
        _itemsState.value = currentList
    }

    fun changeCheckedState(item: ItemModel) {
        val currentData = _itemsState.value.toMutableList()
        val newItem = item.copy(isChecked = !item.isChecked)
        val index = currentData.indexOf(item)
        currentData[index] = newItem
        _itemsState.value = currentData
    }

}