package com.example.notes.viewmodel

import com.example.notes.dao.NoteDao
import com.example.notes.entities.Note

class NoteRepository(val noteDao: NoteDao) {

    suspend fun searchNotes(query: String?): List<Note> {
        return noteDao.searchNotes(query)
    }
}