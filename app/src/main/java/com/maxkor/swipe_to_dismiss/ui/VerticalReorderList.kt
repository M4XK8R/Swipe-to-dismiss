package com.maxkor.swipe_to_dismiss.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.maxkor.swipe_to_dismiss.domain.ItemModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.ReorderableLazyListState
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalReorderList(
    data: State<List<ItemModel>>,
    reorderableState: ReorderableLazyListState,
    viewModel: MainViewModel
) {
    LazyColumn(
        state = reorderableState.listState,
        modifier = Modifier
            .reorderable(reorderableState)
            .detectReorderAfterLongPress(reorderableState)
    ) {
        items(data.value, { it.id }) { item ->
            ReorderableItem(
                reorderableState = reorderableState,
                key = item.id,
                modifier = Modifier.padding(5.dp)
            ) { isDragging ->
                val elevation = animateDpAsState(
                    targetValue = if (isDragging) 16.dp else 0.dp,
                    label = "MyDpAnimation"
                )

                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                ) {
                    val dismissState = rememberDismissState()
                    if (dismissState.isDismissed(DismissDirection.StartToEnd) or
                        dismissState.isDismissed(DismissDirection.EndToStart)
                    ) {
                        viewModel.removeItem(item)
                    }
                    SwipeToDelete(
                        dismissState = dismissState,
                        dismissContent = {
                            ItemCard(
                                id = item.id,
                                text = item.text,
                                isChecked = item.isChecked,
                                changeCheckedState = {
                                    viewModel.changeCheckedState(item)
                                },
                            )
                        }
                    )
                }
            }
        }
    }
}