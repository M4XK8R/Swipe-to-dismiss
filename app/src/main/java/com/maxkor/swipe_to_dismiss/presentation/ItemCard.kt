package com.maxkor.swipe_to_dismiss.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.maxkor.swipe_to_dismiss.R
import kotlin.random.Random

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    id: Int = 0,
    text: String = "Lorem ipsum... $id",
    isChecked: Boolean = Random.nextBoolean(),
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 5.dp,
                end = 3.dp,
                top = 3.dp,
            )
            .then(modifier)
    ) {
        Row(
            Modifier.padding(
                start = 16.dp,
//                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.av2),
                contentDescription = "test image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillHeight
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = text,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily.SansSerif,
//                fontWeight = FontWeight(400)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                var checkedState by remember {
                    mutableStateOf(isChecked)
                }
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = !checkedState
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ItemPreview() {
    ItemCard()
}