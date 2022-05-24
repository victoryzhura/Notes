package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.database.NotesDatabaseDao

class CommonViewModelFactory (
        private val dataSource: NotesDatabaseDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(NotesDatabaseDao::class.java)
                .newInstance(dataSource)

        }

    }
