package com.example.notebook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.model.Note
import com.example.notebook.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app : Application,
private val noteRepository: NoteRepository) : AndroidViewModel(app) {

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun getAllNote() = noteRepository.getAllNotes()

    fun searchNote(query : String?) = noteRepository.searchNotes(query)



}