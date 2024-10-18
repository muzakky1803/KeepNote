package com.example.keepnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao: NoteDao = AppDatabase.getDatabase(application).noteDao()
    private val _notes = MutableLiveData<List<Note>>() // Pastikan tipe data adalah List<Note>
    val notes: LiveData<List<Note>> get() = _notes

    fun insert(note: Note) {
        viewModelScope.launch { // Menggunakan viewModelScope untuk menjalankan coroutine
            noteDao.insert(note)
            loadNotes() // Memanggil loadNotes untuk memperbarui LiveData setelah menyisipkan
        }
    }

    private suspend fun loadNotes() {
        // Mengambil semua catatan dari DAO dan memperbarui LiveData
        _notes.postValue(noteDao.getAllNotes())
    }
}
