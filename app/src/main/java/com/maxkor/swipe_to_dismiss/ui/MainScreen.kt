package com.maxkor.swipe_to_dismiss.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.burnoutcrew.reorderable.rememberReorderableLazyListState

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {

    val data = viewModel.itemsState

    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            viewModel.swapElements(from, to)
        }
    )

    VerticalReorderList(
        data = data,
        reorderableState = reorderableState,
        viewModel = viewModel,
    )

}