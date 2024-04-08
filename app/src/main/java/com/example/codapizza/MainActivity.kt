package com.example.codapizza

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.codapizza.model.Topping
import com.example.codapizza.model.ToppingPlacement
import com.example.codapizza.ui.ToppingCell

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToppingCell(
                topping = Topping.Pepperoni,
                placement = ToppingPlacement.Left,
                onClickTopping = {}
            )
        }
    }
}