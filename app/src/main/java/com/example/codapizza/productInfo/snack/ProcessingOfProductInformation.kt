package com.example.codapizza.productInfo.snack

import android.util.Log
import com.example.codapizza.model.Pizzas
import com.example.codapizza.productInfo.drinks.DrinkInfo

class ProcessingOfProductInformation(
    productName: String,
    pizzaName: String
) {
    private var productImage: Int = 0
    private var productDescription: Int = 0
    private var productIngredients: Int = 0


    private fun checkProductClass(productName: String, pizzaName: String) {
        Log.d("info_order", "$productName , $pizzaName")
        productImage = if(productName != "null") {
            try {
                DrinkInfo.valueOf(productName).drinkImage
            } catch (e: Exception) {
                SnackInfo.valueOf(productName).snackImage
            }
        } else {
            Pizzas.valueOf(pizzaName).pizzaImage
        }
        productDescription = if(productName != "null") {
            try {
                DrinkInfo.valueOf(productName).drinkName
            } catch (e: Exception) {
                SnackInfo.valueOf(productName).snackName
            }
        } else {
            Pizzas.valueOf(pizzaName).pizzaName
        }
        productIngredients = if(pizzaName != "null") {
            Pizzas.valueOf(pizzaName).pizzaIngredients
        } else {
            0
        }
    }

    fun getProductImage(): Int {
        return productImage
    }

    fun getProductDescription(): Int {
        return productDescription
    }

    fun getProductIngredients(): Int {
        return productIngredients
    }

    init {
        checkProductClass(productName, pizzaName)
    }
}
