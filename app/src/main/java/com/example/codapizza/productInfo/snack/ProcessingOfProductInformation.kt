package com.example.codapizza.productInfo.snack

import com.example.codapizza.R
import com.example.codapizza.productInfo.drinks.DrinkInfo

class ProcessingOfProductInformation(
    productName: Int?
) {
    private var productImage: Int = 0
    private var productDescription: Int = 0


    private fun checkProductClass(productName: Int?) {
        productImage = when (productName) {
            R.string.coca_cola -> DrinkInfo.CocaCola.drinkImage
            R.string.fanta -> DrinkInfo.Fanta.drinkImage
            R.string.sprite -> DrinkInfo.Sprite.drinkImage
            R.string.Nuggets -> SnackInfo.Nuggets.snackImage
            R.string.french_fries -> SnackInfo.FrenchFries.snackImage
            else -> SnackInfo.FrenchFries.snackImage
        }
        productDescription = when (productName) {
            R.string.coca_cola -> DrinkInfo.CocaCola.drinkName
            R.string.fanta -> DrinkInfo.Fanta.drinkName
            R.string.sprite -> DrinkInfo.Sprite.drinkName
            R.string.Nuggets -> SnackInfo.Nuggets.snackName
            R.string.french_fries -> SnackInfo.FrenchFries.snackName
            else -> SnackInfo.FrenchFries.snackName
        }
    }

    fun getProductImage(): Int {
        return productImage
    }

    fun getProductDescription(): Int {
        return productDescription
    }

    init {
        checkProductClass(productName)
    }
}
