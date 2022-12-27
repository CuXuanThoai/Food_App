package com.example.foody.ui.fragment.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.foody.databinding.RecipesButtomSheetBinding
import com.example.foody.viewmodel.RecipesViewModel
import com.example.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import java.util.*

class RecipesButtonSheet : BottomSheetDialogFragment() {

    private lateinit var binding :RecipesButtomSheetBinding
    private lateinit var recipesViewModel: RecipesViewModel
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private  var dietType = DEFAULT_DIET_TYPE
    private var dietTypeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipesButtomSheetBinding.inflate(layoutInflater,container,false)

        // Inflate the layout for this fragment
        recipesViewModel.readMealDietType.asLiveData().observe(viewLifecycleOwner){value ->
            mealTypeChip = value.selectedMeal
            mealTypeChipId = value.selectedMealId
            dietType = value.selectedDiet
            dietTypeId = value.selectDietTypeId

            updateChip(value.selectedMealId,binding.mealTypeChipGroup)
            updateChip(value.selectDietTypeId,binding.dietTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedChangeListener{
            group, selectedChipId ->
            val chip  = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }
        binding.dietTypeChipGroup.setOnCheckedChangeListener{
                group, selectedChipId ->
            val chip  = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietType = selectedDietType
            dietTypeId = selectedChipId
        }

        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealDietType(
                mealTypeChip,mealTypeChipId,dietType,dietTypeId
            )
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e : Exception){
                Log.i("bottomSheet",e.message.toString())
            }
        }
    }

}