package com.example.a26_rafa_roomdb

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a26_rafa_roomdb.room.Movie

abstract class Movieadapter (var movies : ArrayList<Movie>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<Movieadapter.MovieViewHolder>(){

    override fun OnCreateViewHolder(parent: ViewGroup, ViewType: Int): MovieHolder {
        return MovieViewHolder(
            layoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.view.text_title.text = movie_title
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<movie>){
        movies.clear()
        movies.AddAll(list)
        notifyDataSetChanged()
    }
}