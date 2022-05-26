package com.example.notes.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.database.NotesDatabaseDao
import com.example.notes.entity.NoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val database: NotesDatabaseDao) : ViewModel() {
    var listOfNotes = database.getAllNotes()

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

    fun delete (key: Int){
        viewModelScope.launch(Dispatchers.IO) {
            database.delete(key)
        }
    }


}