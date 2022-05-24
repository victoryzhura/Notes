package com.example.notes.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.database.NotesDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val database: NotesDatabaseDao) : ViewModel() {
    var listOfNotes = database.getAllNotes()

    fun delete (key: Int){
        viewModelScope.launch(Dispatchers.IO) {
            database.delete(key)
        }
    }
}