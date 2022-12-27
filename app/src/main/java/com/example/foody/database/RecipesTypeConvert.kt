package com.example.foody.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.foody.data.FoodRecipes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConvert {

    var gson = Gson()
    //FoodRecipes class contains list Result

    @TypeConverter
    fun foodRecipeToString (foodRecipes: FoodRecipes): String{
        return gson.toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToFoodRecipe(data :String) :FoodRecipes{
        val listType = object : TypeToken<FoodRecipes>(){}.type
        return gson.fromJson(data,listType)

    }
}