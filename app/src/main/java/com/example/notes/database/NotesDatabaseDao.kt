package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.entity.NoteItem

@Dao
interface NotesDatabaseDao {

    @Insert
    suspend fun insert(note: NoteItem)

    @Query("DELETE from notes_table WHERE id = :key")
    suspend fun delete(key: Int)

    @Update
    suspend fun update(note: NoteItem)

    @Query("SELECT * from notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<NoteItem>>
}