package com.example.codapizza.pizza

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.codapizza.R
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement


@Composable
fun ToppingCellDialog(
    onDismissRequest: () -> Unit,
    onSetToppingPlacement: (placement: ToppingPlacement?) -> Unit,
    placementIsChoose: ToppingPlacement?,
    topping: Topping
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card (
            modifier = Modifier
                .clip(RoundedCornerShape(26.dp))
        ) {
            Column (
                modifier = Modifier
                    .padding(start = 4.dp)
            ) {
                val toppingName = stringResource(id = topping.toppingName)
                Text(
                    text = stringResource(R.string.placement_prompt, toppingName),
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(24.dp)
                )
                
                ToppingPlacement.entries.forEach { placement ->
                    ToppingPlacementOption(
                        placementName = placement.label,
                        placement = placement,
                        placementIsChoose = placementIsChoose,
                        onClick = {
                            onSetToppingPlacement(placement)
                            onDismissRequest()
                        }
                    )
                }
                ButtonDialog(
                    onClick = {
                        onSetToppingPlacement(null)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}


@Composable
fun ToppingPlacementOption(
    @StringRes placementName: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placement: ToppingPlacement?,
    placementIsChoose: ToppingPlacement?,
    checkedColor: Color = Color.Blue,
    uncheckedColor: Color = Color.White,
    size: Float = 24f,
) {

    val checkboxColor: Color by animateColorAsState(if (placementIsChoose != null && placementIsChoose == placement) checkedColor else uncheckedColor,
        label = ""
    )
    val density = LocalDensity.current
    val duration = 1000

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(16.dp, 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = checkboxColor, shape = RoundedCornerShape(20.dp))
                .clickable { onClick() }
                .border(width = 2.dp, color = Color.Yellow, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = (placementIsChoose != null && placementIsChoose == placement),
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = uncheckedColor
                )
            }
        }

        Column {
            Text(
                text = stringResource(id = placementName),
                modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ButtonDialog(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier
            .fillMaxWidth()

    ) {
        Text(
            text = "None",
            modifier.padding(8.dp)
        )
    }
}