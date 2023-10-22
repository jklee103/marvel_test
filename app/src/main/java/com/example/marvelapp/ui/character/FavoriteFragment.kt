package com.example.marvelapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentFavoriteBinding
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding

    private val viewModel: CharacterViewModel by viewModels()

    private val rvAdapter by lazy {
        SearchRvAdapter {
            val oldSet = Prefs.getStringSet(getString(R.string.pref_favorite_set), setOf())
            oldSet.remove(it.id.toString())
            deleteItem(it)
        }
    }

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
    }

    override fun onResume() {
        super.onResume()
        rvAdapter.setClear()
        viewModel.getFavorites()
    }

    private fun setObserver() {
        viewModel.favoriteData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvEmptyMsg.visibility = View.GONE
            } else {
                binding.tvEmptyMsg.visibility = View.VISIBLE
            }
            it.forEach { character ->
                val gson = Gson()
                if (Prefs.getStringSet(getString(R.string.pref_favorite_set), setOf())
                        .contains(gson.toJson(character))
                ) character.isFavorite = true
            }
            rvAdapter.setItems(it)
        }
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        }
    }

    private fun deleteItem(item: com.example.marvelapp.domain.model.Character) {
        rvAdapter.deleteItem(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavoriteFragment()
    }
}