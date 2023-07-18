package com.example.notes.viewmodel

import androidx.lifecycle.*
import com.example.notes.entities.Note
import kotlinx.coroutines.Dispatchers

class NoteViewModel(val repo: NoteRepository) : ViewModel() {

    private val _searchQuery = MutableLiveData<String>()

    val searchResult: LiveData<List<Note>> = _searchQuery.switchMap { query ->
        liveData(Dispatchers.IO) {
            emit(repo.searchNotes(query))
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}