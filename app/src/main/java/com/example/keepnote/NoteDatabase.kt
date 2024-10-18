package com.example.keepnote

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

class NoteDatabase(context: Context) {
    private val dbName = "keep_note.db"
    private val dbTable = "notes"
    private val dbVersion = 1
    private val db: SQLiteDatabase

    init {
        db = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null)
        createTable()
    }

    // Membuat tabel jika belum ada
    private fun createTable() {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $dbTable (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                content TEXT NOT NULL,
                created_at INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    // Menambahkan catatan
    fun addNote(note: Note): Boolean {
        return try {
            val values = ContentValues().apply {
                put("title", note.title)
                put("content", note.content)
                put("created_at", note.createdAt)
            }
            db.insert(dbTable, null, values) > 0
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }
    }

    // Mengedit catatan
    fun updateNote(note: Note): Boolean {
        return try {
            val values = ContentValues().apply {
                put("title", note.title)
                put("content", note.content)
            }
            db.update(dbTable, values, "id = ?", arrayOf(note.id.toString())) > 0
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }
    }

    // Menghapus catatan
    fun deleteNote(noteId: Long): Boolean {
        return try {
            db.delete(dbTable, "id = ?", arrayOf(noteId.toString())) > 0
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }
    }

    // Mengambil semua catatan
    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM $dbTable ORDER BY created_at DESC", null)

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                    val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                    val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow("created_at"))

                    notes.add(Note(id, title, content, createdAt))
                } while (cursor.moveToNext())
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return notes
    }

    // Menutup database
    fun close() {
        db.close()
    }
}
