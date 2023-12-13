package com.maxkor.swipe_to_dismiss.presentation.reorder

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maxkor.swipe_to_dismiss.presentation.item.ItemCard
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalReorderList(
//    verticalReorderContent: @Composable () -> Unit,
    viewModel: VerticalReorderViewModel = viewModel()
) {

    val data = remember { viewModel.itemsState }

    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            viewModel.swapElements(from, to)
        }
    )

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

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        },
                        dismissContent = {
                            ItemCard(
                                id = item.id,
                                text = item.text,
                                isChecked = item.isChecked,
                                changeCheckedState = { viewModel.changeCheckedState(item) })
                        }
                    )
                }
            }
        }
    }
}