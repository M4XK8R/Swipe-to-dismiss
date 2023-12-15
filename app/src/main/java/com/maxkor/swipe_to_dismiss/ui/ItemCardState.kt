package com.maxkor.swipe_to_dismiss.ui

sealed class ItemCardState {
    data object Read : ItemCardState()
    data object Edit : ItemCardState()
    data object Initial : ItemCardState()
}