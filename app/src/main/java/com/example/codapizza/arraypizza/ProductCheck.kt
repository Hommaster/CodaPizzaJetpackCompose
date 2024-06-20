package com.example.codapizza.arraypizza

import com.example.codapizza.model.Pizzas
import com.example.codapizza.productInfo.drinks.DrinkInfo
import com.example.codapizza.productInfo.snack.SnackInfo

class ProductCheck(pizza: Pizzas?, snack: SnackInfo?, drink: DrinkInfo?) {

    private var productImage: Int = 0
    private var productName: Int = 0
    private var productIngredients: Int? = null

    private fun changeProduct(pizza: Pizzas?, snack: SnackInfo?, drink: DrinkInfo?){
        if(snack != null && pizza == null && drink == null) {
            productImage = snack.snackImage
            productName = snack.snackName
            productIngredients = null
        } else if(snack == null && pizza == null && drink != null) {
            productImage = drink.drinkImage
            productName = drink.drinkName
            productIngredients = null
        }
        else{
            productImage = pizza!!.pizzaImage
            productName = pizza.pizzaName
            productIngredients = pizza.pizzaIngredients
        }
    }

    fun getProductImageInt(): Int {
        return productImage
    }
    fun getProductNameInt(): Int {
        return productName
    }
    fun getProductIngredientsInt(): Int? {
        return productIngredients
    }

    init {
        changeProduct(pizza, snack, drink)
    }


}