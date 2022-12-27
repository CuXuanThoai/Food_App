package com.example.foody.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.foody.data.Result

class RecipesDiffUtil(
    private  val  oddList : List<Result>,
    private val newList : List<Result>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oddList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return  oddList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oddList[oldItemPosition] === newList[newItemPosition]
    }
}