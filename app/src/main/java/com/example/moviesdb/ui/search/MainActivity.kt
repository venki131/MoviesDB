package com.example.moviesdb.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdb.R
import com.example.moviesdb.data.model.Search
import com.example.moviesdb.ui.MovieSearchViewModelFactory
import com.example.moviesdb.ui.details.DetailsActivity
import com.example.moviesdb.ui.search.adapters.MoviesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), KodeinAware,
    MoviesListAdapter.ClickCallback {

    override val kodein by closestKodein()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val viewModelFactory: MovieSearchViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
        initSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val subMenu = menu?.getItem(0)?.subMenu
        if (subMenu?.equals("Movies")!!) {
            subMenu.item.isChecked = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    private var searchList : List<Search> = listOf()
    private fun bindUI() = uiScope.launch {
        val searchResult = viewModel.searchResults.await()

        searchResult.observe(this@MainActivity, Observer {
            if (it == null) return@Observer
            initRecyclerView(it.search)
            searchList = it.search
            progressCircular.visibility = View.GONE
            txtSearchMovie.visibility = View.GONE
            println("Debug : desc = ${it.search}")
        })

    }

    private fun initRecyclerView(result: List<Search>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter =
            MoviesListAdapter(
                result,
                this,
                this
            )
        recyclerView.adapter = adapter
    }

    private fun initSearch() {
        val manager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem: SearchView = toolbar.menu.findItem(R.id.search).actionView as SearchView
        toolbar.menu.findItem(R.id.filter).subMenu.item.isChecked = true
        searchItem.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchItem.queryHint = "Search"
        searchItem.isIconified = false
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! >= 3) {
                    progressCircular.visibility = View.VISIBLE
                    txtSearchMovie.visibility = View.GONE
                    viewModel.getSearchInputs("movie", query)
                    bindUI()
                }
                searchItem.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onClick(position: Int) {
        intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("Title", searchList[position].title)
        startActivity(intent)
    }
}
