package com.example.notes.detailnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.database.NotesDatabaseDao
import com.example.notes.entity.NoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailNoteViewModel(private val database: NotesDatabaseDao) : ViewModel() {

    fun insert(note: NoteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            database.insert(note)
        }
    }

    fun update(note: NoteItem) {
        viewModelScope.launch(Dispatchers.IO) {
            database.update(note)
        }
    }
}