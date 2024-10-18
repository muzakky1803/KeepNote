package com.example.keepnote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LabelDao {
    @Insert
    suspend fun insert(label: Label)

    @Query("SELECT * FROM labels")
    suspend fun getAllLabels(): List<Label>
}