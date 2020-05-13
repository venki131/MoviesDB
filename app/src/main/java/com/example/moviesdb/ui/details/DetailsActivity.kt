package com.example.moviesdb.ui.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.ui.MovieSearchViewModelFactory
import com.example.moviesdb.ui.search.MainViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class DetailsActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val viewModelFactory: MovieSearchViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel
    private val plot = "full"
    private lateinit var movieTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        getArgs()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
        viewModel.getDetails(plot, movieTitle)
        bindUI()
    }

    private fun bindUI() = uiScope.launch {
        val searchResult = viewModel.movieDetails.await()

        searchResult.observe(this@DetailsActivity, Observer {
            if (it == null) return@Observer
            findViewById<TextView>(R.id.txtTitle).text = it.title
            findViewById<TextView>(R.id.year).text = it.year
            findViewById<TextView>(R.id.rating).text = it.imdbRating
            findViewById<TextView>(R.id.genre).text = it.genre
            findViewById<TextView>(R.id.plot).text = it.plot
            Glide.with(this@DetailsActivity)
                .load(it.poster)
                .into(findViewById(R.id.poster))
            println("Debug : desc = ${it.title}")
        })

    }

    private fun getArgs() {
        movieTitle = intent.getStringExtra("Title")
    }
}
