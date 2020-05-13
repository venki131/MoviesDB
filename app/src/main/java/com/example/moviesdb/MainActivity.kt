package com.example.moviesdb

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSearch()
        initRecyclerView(/*listOf()*/)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val subMenu = menu?.getItem(0)?.subMenu
        if (subMenu?.equals("Movies")!!) {
            subMenu.item.isChecked = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initRecyclerView(/*sortedBakersList: List<BakersResponseModel>*/) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MoviesListAdapter()
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
                searchItem.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query?.length!! >= 3) {
                    println(query)
                }
                return true
            }
        })
    }
}
