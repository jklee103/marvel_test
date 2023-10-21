package com.example.marvelapp.ui.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemRvCharacterBinding
import com.example.marvelapp.domain.model.Character

typealias OnRecyclerViewItemClick<T> = ((T) -> Unit)

class SearchRvAdapter(private val onItemClick: OnRecyclerViewItemClick<Character>): RecyclerView.Adapter<SearchRvAdapter.CharacterVH>() {
    private val items: MutableList<Character> = mutableListOf()

    fun setClear(){
        this.items.clear()
        notifyDataSetChanged()
    }
    fun setItems(items: List<Character>) {
        this.items.addAll(items)
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
                onItemClick.invoke((currentItem))
            }
        }
    }
}