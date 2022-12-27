package com.example.foody.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.data.FoodRecipes
import com.example.foody.util.Constants.Companion.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipes: FoodRecipes) {
    @PrimaryKey(autoGenerate = false)
    var id  :Int = 0
}