package com.example.keepnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadOnlyProperty

class NoteActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels()

    private fun viewModels(): ReadOnlyProperty<NoteActivity, NoteViewModel> {
        TODO("Not yet implemented")
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter // Pastikan Anda sudah membuat adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati perubahan data pada LiveData
        noteViewModel.notes.observe(this, Observer { notes ->
            // Update UI dengan daftar catatan
            notes?.let { adapter.submitList(it) } // Pastikan Anda mengimplementasikan submitList di adapter
        })

        // Inisialisasi adapter
        adapter = NoteAdapter() // Pastikan Anda sudah mengimplementasikan adapter
        recyclerView.adapter = adapter

        // Contoh menyisipkan catatan baru
        val newNote = Note(title = "Judul Catatan", content = "Isi Catatan", categoryId = 1)
        noteViewModel.insert(newNote)
    }
}
