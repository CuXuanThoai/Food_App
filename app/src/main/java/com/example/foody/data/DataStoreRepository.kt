package com.example.foody.data

import android.content.Context
import android.os.Build.VERSION_CODES.P
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.*
import com.example.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foody.util.Constants.Companion.PREFERENCES_MEAL
import com.example.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foody.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private object PreferenceKeys {
        val sealMealtype = preferencesKey<String>(PREFERENCES_MEAL)
        val seletectedMealTypeId = preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore : DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )


    data class MealDietType(
        val selectedMeal :String ,
        val selectedMealId: Int,
        val selectedDiet: String,
        val selectDietTypeId :Int
    )
    val  readMealAndDietType :Flow<MealDietType> = dataStore.data
        .catch { exeption ->
            if (exeption is IOException){
                emit(emptyPreferences())
            }
            else{
                throw exeption
            }
        }
        .map {  preference ->
            val selectMeal = preference[PreferenceKeys.sealMealtype] ?: "main course"
            val selectedMealId = preference[PreferenceKeys.seletectedMealTypeId] ?: 0
            val selectedDiet = preference[PreferenceKeys.selectedDietType] ?:"gluten free"
            val selectDietTypeId = preference[PreferenceKeys.selectedDietTypeId] ?: 0

            MealDietType(selectMeal,selectedMealId,selectedDiet,selectDietTypeId)
        }

   suspend fun saveMealAndDiet(mealType :String,mealTypeId:Int,dietType :String,dietTypeId :Int){
      dataStore.edit { preferences ->
          preferences[PreferenceKeys.sealMealtype] = mealType
          preferences[PreferenceKeys.seletectedMealTypeId] = mealTypeId
          preferences[PreferenceKeys.selectedDietType] = dietType
          preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
      }
    }

}