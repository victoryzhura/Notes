package com.example.notes.notes

import androidx.lifecycle.ViewModel
import com.example.notes.database.NotesDatabaseDao

class NotesViewModel(database: NotesDatabaseDao) : ViewModel() {
    var listOfNotes = database.getAllNotes()
}