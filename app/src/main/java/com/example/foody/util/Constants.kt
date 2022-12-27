package com.example.foody.util

class Constants {
    companion object{
        const val API_KEY = "f5fdfdf490384658ae55c8efbe604019"
        const val BASE_URL = "https://api.spoonacular.com"

        // API QUERY
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET ="diet"
        const val QUERY_ADD_RECIPE_INFORMATION ="addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        //Bottom sheet and preference

        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"

        const val PREFERENCES_NAME = "food_preference"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID ="dietTypeId"
        const val PREFERENCES_MEAL ="mealType"
        const val PREFERENCES_MEAL_TYPE_ID ="mealTypeId"

    }
}