package com.example.notes.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.R
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes_table")

@Parcelize
data class NoteItem(
    var color: Int = R.color.amber_900,
    var title: String? = "",
    var text: String? = "",

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var isPined: Boolean = false,

    @ColumnInfo(name = "time_notes")
    var time: Long = System.currentTimeMillis()
) : Parcelable