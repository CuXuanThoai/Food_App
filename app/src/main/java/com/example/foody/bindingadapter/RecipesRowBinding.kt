package com.example.foody.bindingadapter

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foody.R
import com.example.foody.data.FoodRecipes
import com.example.foody.database.RecipesEntity
import com.example.foody.util.NetworkResult

class RecipesRowBinding {

    companion object{

        @BindingAdapter("setNumberOfLike")
        @JvmStatic
        fun setNumberOfLike(text: TextView,int: Int){
            text.text = int.toString()
        }
        @BindingAdapter("setNumberOfMinute")
        @JvmStatic
        fun setNumberOfMinute(text: TextView,int: Int){
            text.text = int.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View,vegan : Boolean){
            if (vegan){
                when(view){
                    is TextView -> {view.setTextColor(ContextCompat.getColor(view.context, R.color.green))}

                    is  ImageView ->{view.setColorFilter(ContextCompat.getColor(view.context,R.color.green))}
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl (imageView: ImageView,image :String){
            imageView.load(image){
                crossfade(500)
            }
        }

        @BindingAdapter("readApiResponse","readDatabase",requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<FoodRecipes>?,
            database: List<RecipesEntity>?
        ){
            when (view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }
    }

    }
