package com.maxkor.swipe_to_dismiss.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalReorderList() {

    val data = remember {
        mutableStateOf(List(30) { ItemModel(id = it) })
    }

    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            data.value = data.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }
    )

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value, { it.id }) { item ->

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
                        data.value = currentList.toList()
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
                                text = item.text
                            )
                        }
                    )
                }
            }
        }
    }
}