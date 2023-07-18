package com.example.notes.dao

import androidx.room.*
import com.example.notes.entities.Note


@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR subtitle LIKE '%' || :query || '%'")
    suspend fun searchNotes(query: String?): List<Note>

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)


}