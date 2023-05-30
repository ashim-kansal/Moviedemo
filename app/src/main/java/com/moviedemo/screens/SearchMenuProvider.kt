package com.moviedemo.screens

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.MenuProvider
import com.moviedemo.R
import com.moviedemo.data.MediaViewModel


class SearchMenuProvider(
    private val itemViewModel: MediaViewModel) : MenuProvider {


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search"
        val hintIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        hintIcon?.visibility = View.GONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null && newText.length>2){
                    itemViewModel.searchResults(newText)
                }else{
                    itemViewModel.searchResults("")
                }
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }


}