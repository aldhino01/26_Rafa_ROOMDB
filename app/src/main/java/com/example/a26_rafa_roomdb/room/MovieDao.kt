package com.example.a26_rafa_roomdb.room

import androidx.room.*

@Dao
interface MovieDao {

    @Insert
    suspend fun addMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie ORDER BY id DESC")
    suspend fun getMovies() : List<Movie>

    @Query("SELECT * FROM note WHERE id=:note_id")
    suspend fun getMovie(movie_id: Int) : List<Movie>
}