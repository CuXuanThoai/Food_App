package com.example.foody.data.network

import com.example.foody.FoodRecipesAPI
import com.example.foody.data.FoodRecipes
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipesAPI : FoodRecipesAPI){

    suspend fun getRecipes(queries :Map<String,String> ) : Response<FoodRecipes> {
        return  foodRecipesAPI.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery : Map<String,String>) :Response<FoodRecipes> {
        return  foodRecipesAPI.searchRecipes(searchQuery)
    }

}