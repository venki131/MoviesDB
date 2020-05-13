package com.example.moviesdb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.data.model.Search

class MoviesListAdapter(private val result: List<Search>, private val context: Context) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            search: Search,
            context: Context
        ) {
            val title = itemView.findViewById<TextView>(R.id.txtMovieTitle)
            val poster = itemView.findViewById<AppCompatImageView>(R.id.imgAlbumCover)

            title.text = search.title
            Glide.with(context)
                .load(search.poster)
                .into(poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindItems(result[position], context)
    }


}