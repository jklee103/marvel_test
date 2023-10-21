package com.example.marvelapp.ui.character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharacterVPAdapter
    private val tabList by lazy {
        linkedMapOf<String, Fragment>(
            "SEARCH" to SearchFragment.newInstance(),
            "FAVORITE" to FavoriteFragment.newInstance()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setupUI()
    }

    private fun setupUI() {
        adapter = CharacterVPAdapter(this)
        binding.vpCharacter.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.vpCharacter) { tab, pos ->
            tab.text = tabList.keys.elementAt(pos)
        }.attach()
    }

    inner class CharacterVPAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = tabList.size

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            return tabList.values.elementAt(position)
        }

    }
}