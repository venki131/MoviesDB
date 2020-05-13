package com.example.moviesdb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.R

class MoviesListAdapter() : RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems() {
            val title = itemView.findViewById<TextView>(R.id.txtMovieTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindItems()
    }


}