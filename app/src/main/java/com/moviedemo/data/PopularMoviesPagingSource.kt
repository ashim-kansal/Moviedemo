package com.moviedemo.data

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practicaltest.data.model.MoveListModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moviedemo.data_models.Movie
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type

private const val STARTING_PAGE_INDEX = 1

class PopularMoviesPagingSource(val context: Context, val text:String) : PagingSource<Int, MoveListModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoveListModel> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val results = getDataFromFile(position)
            var newResults = ArrayList<MoveListModel>()
            if(text.length>0){
                for (contentItem in results.page.content.content_items) {
                    if(contentItem.name.contains(text, true))
                        newResults.add(contentItem)
                }
            }else{
                newResults.addAll(results.page.content.content_items)
            }

            LoadResult.Page(
                    data = newResults,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (position == 3) null else position+1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    private fun getDataFromFile(position: Int): Movie {
        val typeToken: Type = object : TypeToken<Movie?>() {}.type
        var data = getJSONFromAssets("CONTENTLISTINGPAGE-PAGE$position.json")
        return Gson().fromJson(data, typeToken)
    }
    fun getJSONFromAssets(fileName: String): String? {
        return try {
            val stream: InputStream =
                context.assets.open(fileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }


    override fun getRefreshKey(state: PagingState<Int, MoveListModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}