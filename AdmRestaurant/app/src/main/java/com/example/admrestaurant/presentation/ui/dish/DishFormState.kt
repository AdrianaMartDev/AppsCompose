package com.example.admrestaurant.presentation.ui.dish

data class DishFormState(
    var nomDish: String = "",
    var descDish: String = "",
    var priceDish: String = "",
    var categoryDish: String = "Select category",
    var showCategory: Boolean = false
) {
    val priceAsDouble: Double
        get() = priceDish.toDoubleOrNull() ?: 0.0

    val isValid: Boolean
        get() = nomDish.isNotBlank()
                && descDish.isNotBlank()
                && priceDish.isNotBlank()
                && categoryDish != "Select category"

}