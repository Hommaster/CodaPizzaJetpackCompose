package com.example.codapizza.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement


@Preview
@Composable
private fun ToppingCellPreview() {
    ToppingCell(
        topping = Topping.Pepperoni,
        placement = ToppingPlacement.Left,
        isChecked = true,
        onClickTopping = {}
    )
}

@Preview
@Composable
private fun ToppingCallPreviewNotOnPizza() {
    ToppingCell(topping = Topping.Basil, placement = null,
        isChecked = false,
        onClickTopping = {})
}

@Composable
fun ToppingCell(
    topping: Topping,
    placement: ToppingPlacement?,
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    size: Float = 24f,
    checkedColor: Color = Color.Blue,
    uncheckedColor: Color = Color.White,
    onClickTopping: () -> Unit
) {

    val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor,
        label = ""
    )
    val density = LocalDensity.current
    val duration = 1000

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClickTopping() }
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(color = checkboxColor, shape = RoundedCornerShape(20.dp))
                .clickable { onClickTopping() }
                .border(width = 2.dp, color = Color.Yellow, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
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
        Column (
            modifier = Modifier
                .weight(1f, fill = true)
                .padding(start = 4.dp)

        ) {
            Text(
                text = stringResource(topping.toppingName),
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                style = MaterialTheme.typography.body1
            )
            if (placement != null) {
                Text(
                    text = stringResource(id = placement.label),
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Image(
            painterResource(topping.toppingDrawable),
            "drawableIcon"
        )
    }
}