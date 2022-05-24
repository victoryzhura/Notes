package com.example.notes.notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.ItemNotesBinding
import com.example.notes.entity.NoteItem

class NotesAdapter :
    androidx.recyclerview.widget.ListAdapter<NoteItem, NotesAdapter.NotesHolder>(
        NotesCallback()
    ) {

    inner class NotesHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(item: NoteItem) {
            binding.oneTextItem = item
            binding.cardTextNotes.setCardBackgroundColor(item.color)
            binding.timeNotes.isSelected = true
            binding.textNotesTitle.isSelected = true
            binding.executePendingBindings()
            Log.d("test1", "hghhg")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val binding: ItemNotesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_notes,
            parent, false
        )
        return NotesHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class NotesCallback : DiffUtil.ItemCallback<NoteItem>() {
    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem == newItem
    }
}