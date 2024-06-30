package com.example.codapizza.productInfo.snack

import com.example.codapizza.model.Pizzas
import com.example.codapizza.productInfo.drinks.DrinkInfo

class ProcessingOfProductInformation(
    productName: String,
    pizzaName: String,
    productID: Int
) {
    private var productImage: Int = 0
    private var productName: Int = 0
    private var productIngredients: Int = 0
    private var productPrice: Int = 0
    private var drinkInfoProduct: Boolean = false
    private var notPizzaName: Int? = null
    private var productIDFromOrder: Int = -1


    private fun checkProductClass(productName: String, pizzaName: String, productID: Int) {
        when (productID) {
            0 -> {
                productImage = Pizzas.valueOf(pizzaName).pizzaImage
                this.productName = Pizzas.valueOf(pizzaName).pizzaName
                productIngredients = Pizzas.valueOf(pizzaName).pizzaIngredients
                productPrice = Pizzas.valueOf(pizzaName).price
                drinkInfoProduct = false
            }

            1 -> {
                productImage = SnackInfo.valueOf(productName).snackImage
                this.productName = SnackInfo.valueOf(productName).snackName
                productIngredients = 0
                productPrice = SnackInfo.valueOf(productName).price
                drinkInfoProduct = SnackInfo.valueOf(productName).drinkInfo
                notPizzaName = SnackInfo.valueOf(productName).snackName
                productIDFromOrder = SnackInfo.valueOf(productName).snackName
            }

            2 -> {
                productImage = DrinkInfo.valueOf(productName).drinkImage
                this.productName = DrinkInfo.valueOf(productName).drinkName
                productIngredients = 0
                productPrice = DrinkInfo.valueOf(productName).price
                drinkInfoProduct = DrinkInfo.valueOf(productName).drinkInfo
                notPizzaName = DrinkInfo.valueOf(productName).drinkName
                productIDFromOrder = DrinkInfo.valueOf(productName).drinkName
            }
        }
    }

    fun getProductImage(): Int {
        return productImage
    }

    fun getProductName(): Int {
        return productName
    }

    fun getProductIngredients(): Int {
        return productIngredients
    }

    fun getPriceProduct(): Int {
        return productPrice
    }

    fun getDrinkInfo(): Boolean {
        return drinkInfoProduct
    }

    fun getNotPizzaName(): Int? {
        return notPizzaName
    }

    fun getProductIDFromOrder(): Int {
        return productIDFromOrder
    }

    init {
        checkProductClass(productName, pizzaName, productID)
    }

}
