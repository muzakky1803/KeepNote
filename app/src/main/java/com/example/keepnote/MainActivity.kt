package com.example.keepnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var inputNote: EditText
    private lateinit var saveButton: Button
    private lateinit var noteDatabase: NoteDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inisialisasi view
        inputNote = findViewById(R.id.inputNote)
        saveButton = findViewById(R.id.saveButton)

        // Set listener untuk tombol simpan
        saveButton.setOnClickListener {
            // Ambil teks dari EditText
            val noteText = inputNote.text.toString()

            // Cek jika teks tidak kosong
            if (noteText.isNotEmpty()) {
                // Tampilkan pesan konfirmasi
                Toast.makeText(this, "Catatan disimpan: $noteText", Toast.LENGTH_SHORT).show()

                // Kosongkan input
                inputNote.text.clear()

                // Navigasi ke ListNoteActivity setelah menyimpan
                val intent = Intent(this, ListNoteActivity::class.java)
                startActivity(intent)

            } else {
                // Tampilkan pesan jika input kosong
                Toast.makeText(this, "Tolong masukkan catatan", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
