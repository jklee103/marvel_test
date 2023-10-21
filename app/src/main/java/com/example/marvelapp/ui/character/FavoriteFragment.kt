package com.example.marvelapp.ui.character

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding

    private val viewModel: CharacterViewModel by viewModels()

    private val rvAdapter by lazy { SearchRvAdapter{ Log.e("click favorite", "$it") }}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.rvList.adapter = rvAdapter
        setObserver()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel.getFavorites()
    }
    private fun setObserver(){
        viewModel.favoriteData.observe(viewLifecycleOwner){
            rvAdapter.setItems(it)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FavoriteFragment()
    }
}