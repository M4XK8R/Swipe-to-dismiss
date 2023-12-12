package com.maxkor.swipe_to_dismiss.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.maxkor.swipe_to_dismiss.R
import com.maxkor.swipe_to_dismiss.data.MyMapper
import com.maxkor.swipe_to_dismiss.data.MyRepositoryImpl
import com.maxkor.swipe_to_dismiss.data.MyRoomDataBase
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

private const val TEST_TAG = "test_tag"

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalReorderList() {
    Log.d(TEST_TAG, "VerticalReorderList recompose")

    var counter by remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current
    val database = MyRoomDataBase.getInstance(context)
    val mapper = MyMapper()
    val repository = MyRepositoryImpl(database, mapper)

    val coroutine = rememberCoroutineScope()

    val dbData = repository.getAllItems()
        .collectAsState(initial = mutableListOf())

    val list = dbData.value.toList()

    val data = remember {
        mutableStateOf(list)
    }


    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
//            data.value = data.value.toMutableList().apply {
//                add(to.index, removeAt(from.index))
//            }

            val currentList = data.value.toMutableList()
            val draggedItem = currentList[from.index]
            currentList.remove(draggedItem)
            currentList.add(to.index, draggedItem)

//            data.value = currentList

            val reorderedList = mutableListOf<ItemModel>()
            for (i in 0 until currentList.size) {
                val item = currentList[i].copy(orderId = i)
                reorderedList.add(item)
            }

            coroutine.launch {
                repository.deleteAll()
                repository.insertAllItems(reorderedList)
            }

//            if (job.isCompleted) {
//                data.value = dbData.value
//            }

        }
    )



    Box {

        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .reorderable(state)
                .detectReorderAfterLongPress(state)
        ) {
            items(data.value, key = { it.orderId!! }) { item ->

                ReorderableItem(
                    reorderableState = state,
                    key = item.id,
                    modifier = Modifier.padding(5.dp)
                ) { isDragging ->
                    val elevation = animateDpAsState(
                        if (isDragging) 16.dp else 0.dp
                    )

                    Column(
                        modifier = Modifier
                            .shadow(elevation.value)
//                        .background(Color.Red)
                    ) {

                        val dismissState = rememberDismissState()

                        if (dismissState.isDismissed(DismissDirection.StartToEnd) or
                            dismissState.isDismissed(DismissDirection.EndToStart)
                        ) {
                            val currentList = data.value.toMutableList()
                            currentList.remove(item)
//                            data.value = currentList.toList()
                        }

                        SwipeToDismiss(
                            state = dismissState,
                            background = {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(Color.Red)
                                        .fillMaxSize()

                                ) {
                                    Text(text = "DELETE")
                                }
                            },
                            dismissContent = {
                                ItemCard(
//                                    itemModel = mapper.mapEntityToModel(item),
                                    itemModel = item,
                                    modifier = Modifier.clickable {
                                        coroutine.launch {
//                                            repository.deleteItem(mapper.mapEntityToModel(item))
                                            repository.deleteItem(item)
                                        }
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }

        ExtendedFloatingActionButton(
            onClick = {
//                val currentList = data.value.toMutableList()
//                val id = currentList.size
//                val item = ItemModel(id = id)
//                currentList.add(item)
//                data.value = currentList.toList()
                coroutine.launch {
                    repository.insertItem(ItemModel(id = counter++))
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "Image Add"
            )

            Text(
                text = "Add item",
                fontSize = TextUnit(16f, TextUnitType.Sp)
            )

        }
    }
}





