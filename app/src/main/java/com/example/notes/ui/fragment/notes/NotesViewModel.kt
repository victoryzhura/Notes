package com.example.notes.ui.fragment.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.database.NotesDatabaseDao
import com.example.notes.database.entity.NoteItem
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

    fun clear () {
        viewModelScope.launch(Dispatchers.IO) {
            database.clear()
        }
    }


}