package com.example.foody

import com.example.foody.data.FoodRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesAPI {

    @GET("recipes/complexSearch")
    suspend  fun getRecipes(@QueryMap query: Map<String,String>
    ):Response<FoodRecipes>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap searchQuery: Map<String, String>):Response<FoodRecipes>
}