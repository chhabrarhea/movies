package com.example.movies

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movies.adapter.MovieAdapter
import com.example.movies.api.Client
import com.example.movies.api.Service
import com.example.movies.model.Movie
import com.example.movies.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
     var adapter: MovieAdapter? = null
    private var movieList: List<Movie>? = null
    var pd: ProgressDialog? = null
    private var swipeContainer: SwipeRefreshLayout? = null
    val LOG_TAG = MovieAdapter::class.java.name


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        recyclerView = findViewById(R.id.recycler_view);


    }
    private fun initViews() {
        recyclerView =findViewById(R.id.recycler_view) as RecyclerView
        movieList = ArrayList()
        adapter = MovieAdapter(this@MainActivity, movieList)
        if (getActivity()?.getResources()
            ?.getConfiguration()?.orientation === Configuration.ORIENTATION_PORTRAIT
        ) {
            recyclerView!!.setLayoutManager(GridLayoutManager(this, 2))
        } else {
            recyclerView!!.setLayoutManager(GridLayoutManager(this, 4))
        }
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
        recyclerView!!.setAdapter(adapter)
        adapter!!.notifyDataSetChanged()
        loadJSON()
    }
    fun getActivity(): Activity? {
        var context: Context = this
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = (context as ContextWrapper).baseContext
        }
        return null
    }

    fun loadJSON()
    {
        try {
            val retrofit: Retrofit =Client.getClient()
            val apiService:Service=retrofit.create(Service::class.java)
            val call: Call<MovieResponse> = apiService.getPopularMovies("a334a8b0fb820b10767f9c8c1306143f")
                call?.enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>?,
                    response: Response<MovieResponse?>
                ) {
                    val movies: List<Movie> = response.body()!!.getResults()
                    Collections.sort(movies, Movie.BY_NAME_ALPHABETICAL)
                    recyclerView!!.adapter = MovieAdapter(applicationContext, movies)
                    recyclerView!!.smoothScrollToPosition(0)

                }

                override fun onFailure(call: Call<MovieResponse?>?, t: Throwable) {
                    Log.d("Error", t.message)
                    Toast.makeText(this@MainActivity, "Error Fetching Data!", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            Log.d("Error", e.message)
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }





}