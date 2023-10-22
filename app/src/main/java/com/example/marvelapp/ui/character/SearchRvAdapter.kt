package com.example.marvelapp.ui.character

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemRvCharacterBinding
import com.example.marvelapp.domain.model.Character

typealias OnRecyclerViewItemClick<T> = ((T) -> Unit)

class SearchRvAdapter(private val onItemClick: OnRecyclerViewItemClick<Character>): RecyclerView.Adapter<SearchRvAdapter.CharacterVH>() {
    private val items: MutableList<Character> = mutableListOf()
    init {
        setHasStableIds(true)
    }

    fun setClear(){
        this.items.clear()
        notifyDataSetChanged()
    }
    fun setItems(items: List<Character>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun deleteItem(item: Character){
        items.remove(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterVH {
        return CharacterVH(
            ItemRvCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setClickEvent()
        }
    }

    override fun onBindViewHolder(holder: CharacterVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getItemCount() = items.size
    inner class CharacterVH(private var binding: ItemRvCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            binding.item = item
            binding.executePendingBindings()
        }

        fun setClickEvent() {
            binding.root.setOnClickListener {
                val currentItem =
                    items.getOrNull(adapterPosition) ?: return@setOnClickListener
                if(binding.clBackground.background.constantState == ContextCompat.getDrawable(it.context, R.drawable.favorite_background)?.constantState)
                     binding.clBackground.background = ContextCompat.getDrawable(it.context, R.drawable.default_background)
                else binding.clBackground.background = ContextCompat.getDrawable(it.context, R.drawable.favorite_background)
                onItemClick.invoke((currentItem))
            }
        }
    }
}