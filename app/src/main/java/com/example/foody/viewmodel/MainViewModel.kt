package com.example.foody.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foody.data.FoodRecipes
import com.example.foody.data.Repository
import com.example.foody.database.RecipesEntity
import com.example.foody.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor (
    appication: Application,
    private  val repository: Repository
) : AndroidViewModel(appication) {

    //RomdataBase
    //read database check whether database is change ?
    val readRecipes : LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()
    val searchRecipesResponse :MutableLiveData<NetworkResult<FoodRecipes>> = MutableLiveData()

    private  fun insertRecipes (recipesEntity: RecipesEntity) =
        viewModelScope.launch (Dispatchers.IO ){
            repository.local.insertRecipes(recipesEntity)
        }

    //Retrofit
    var recipesAPI : MutableLiveData<NetworkResult<FoodRecipes>> = MutableLiveData()

    fun getRecipes (query: Map<String,String>) = viewModelScope.launch {
        getRecipesSafeCall(query)
    }

    fun searchRecipes(query: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(query)
    }

    private  suspend  fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val respone = repository.remote.searchRecipes(searchQuery)
                //Call get Recipes from retrofit that using DI
                val reponse = repository.remote.getRecipes(searchQuery)
                searchRecipesResponse.value =
                    handelFoodRecipes(reponse) // after receive response handle it
            }
            catch (e : Throwable){
                    searchRecipesResponse.value = NetworkResult.Error("Recipes not found")
            }
        }
        else
            searchRecipesResponse.value = NetworkResult.Error("No Internet")
    }

    private  suspend fun getRecipesSafeCall(query: Map<String, String>) {
        recipesAPI.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {

                //Call get Recipes from retrofit that using DI
                val reponse = repository.remote.getRecipes(query)
                recipesAPI.value = handelFoodRecipes(reponse) // after receive response handle it
                //response of retrofit (An HTTP response.)
                val foodRecipes = recipesAPI.value!!.data
                if (foodRecipes != null){
                    offlineCacheRecipes(foodRecipes)
                }
            }
            catch (e : Throwable){

            }
        }
        else
            recipesAPI.value = NetworkResult.Error("No Internet")
    }

    private fun offlineCacheRecipes(foodRecipes: FoodRecipes) {
            val recipesEntity = RecipesEntity(foodRecipes)
             insertRecipes(recipesEntity)

    }

    // this function receive reponse http as para and return class custom Network
    private fun handelFoodRecipes(response: Response<FoodRecipes>): NetworkResult<FoodRecipes>? {
        when{
            // if response http contains ->
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Time out")
            }
            response.code() == 402 -> {
                 return NetworkResult.Error("ApiLimited")
            }
            response.body()!!.results.isNullOrEmpty() -> { return  NetworkResult.Error("Recipes not found")}
            // if response http  return success
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else ->  {
               return NetworkResult.Error(response.message())
            }
        }
    }

    private  fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val availableNetwork = connectivityManager.activeNetwork ?: return false
        val capibilities = connectivityManager.getNetworkCapabilities(availableNetwork) ?: return false
        return  when{
            capibilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capibilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            capibilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            else -> false
        }
    }



}