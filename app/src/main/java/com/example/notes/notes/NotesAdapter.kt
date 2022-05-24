package com.example.notes.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.ItemNotesBinding
import com.example.notes.entity.NoteItem
import com.example.notes.utility.Util.getDate


class NotesAdapter(private val callback: (NoteItem) -> Unit, private val longCallback: (NoteItem, View)-> Unit) :
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
            binding.timeNotes.text = getDate(item.time, "EEE, d MMM, yyyy")
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                callback(item)
            }
            binding.root.setOnLongClickListener {
                longCallback(item, binding.root)
                false
            }
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