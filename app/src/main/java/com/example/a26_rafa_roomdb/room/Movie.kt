package com.example.a26_rafa_roomdb.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Movie (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
    )
