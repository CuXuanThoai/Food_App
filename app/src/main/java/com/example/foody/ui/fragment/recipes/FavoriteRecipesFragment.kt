package com.example.foody.ui.fragment.recipes

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.foody.databinding.FragmentFavoriteRecipesBinding


class FavoriteRecipesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteRecipesBinding
    private lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteRecipesBinding.inflate(layoutInflater, container, false)
        textView = binding.textView7
        binding.button.setOnClickListener {
            ObjectAnimator.ofFloat(textView, "translationX", 100f).apply {
                duration = 1000
                start()

            }
        }
        return binding.root
    }
}