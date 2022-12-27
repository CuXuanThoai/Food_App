package com.example.foody

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.data.FoodRecipes
import com.example.foody.data.Result
import com.example.foody.databinding.RecipesRowLayoutBinding
import com.example.foody.ui.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe = emptyList<Result>()
    //Name of layout display one item at recyclerView
   class MyViewHolder(private val binding :RecipesRowLayoutBinding ):RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result ){
            binding.result= result // data binding
            binding.executePendingBindings()
        }
        companion object{
            fun from (parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        Log.i("RecyclerView"," adapter position ${holder.adapterPosition} and layout position ${holder.layoutPosition}")
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }
    //FoodRecipes class contain variable
    fun setData(newData : FoodRecipes){
        val recipesDiffUtil = RecipesDiffUtil(recipe,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }

}