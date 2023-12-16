package com.maxkor.swipe_to_dismiss.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maxkor.swipe_to_dismiss.R

private const val PIC_IMAGE = "image/*"

@Composable
fun ItemCard(
    id: Int,
    text: String,
    isChecked: Boolean,
    changeCheckedState: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val chooseImage = { launcher.launch(PIC_IMAGE) }

    val itemCardState: MutableState<ItemCardState> = remember {
        mutableStateOf(ItemCardState.Read)
    }

    val textState = remember { mutableStateOf(text) }

    val readMode = itemCardState.value == ItemCardState.Read
    val editMode = itemCardState.value == ItemCardState.Edit

    val changeModeToEdit = { itemCardState.value = ItemCardState.Edit }
    val changeModeToRead = { itemCardState.value = ItemCardState.Read }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 3.dp, top = 3.dp)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (imageUri == null) {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "test image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable { chooseImage() },
                    contentScale = ContentScale.FillHeight
                )
            }

            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable { chooseImage() },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            if (readMode) {
                val changeItemCardState = {
                    changeModeToEdit()
                }
                ItemCardText(
                    id = id,
                    textState = textState,
                    changeItemCardState = { changeItemCardState() }
                )
            }

            if (editMode) {
                ItemCardTextEdit(
                    textState = textState,
                    onValueChangeListener = {
                        textState.value = it
                    },
                    confirmTextEdit = { changeModeToRead() }
                )
            }

            Box(
                Modifier.fillMaxSize()
            ) {
                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { changeCheckedState() },
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_swap_vert_32),
                        contentDescription = "Drag and drop",
                    )
                }
            }
        }
    }
}


@Composable
private fun ItemCardTextEdit(
    textState: State<String>,
    onValueChangeListener: (letters: String) -> Unit,
    confirmTextEdit: () -> Unit
) {
    TextField(
        value = textState.value,
        onValueChange = { onValueChangeListener(it) },
        modifier = Modifier.width(100.dp)
    )

    Spacer(modifier = Modifier.size(10.dp))

    IconButton(onClick = { confirmTextEdit() }) {
        Image(
            painter = painterResource(id = R.drawable.outline_add_task_24),
            contentDescription = "Confirm"
        )
    }
}

private const val MAX_TEXT_LINES = 1

@Composable
private fun ItemCardText(
    id: Int,
    textState: MutableState<String>,
    changeItemCardState: () -> Unit
) {
    Text(
        text = "$id ${textState.value}",
        fontSize = TextUnit(20f, TextUnitType.Sp),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = FontFamily.SansSerif,
        maxLines = MAX_TEXT_LINES,
        modifier = Modifier
            .requiredWidthIn(100.dp, 150.dp)
            .clickable { changeItemCardState() },
        softWrap = false,
        overflow = TextOverflow.Ellipsis
    )
}
