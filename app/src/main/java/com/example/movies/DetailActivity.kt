package com.example.movies

import android.R.layout
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.model.Movie


class DetailActivity : AppCompatActivity() {
    var nameOfMovie: TextView? = null
    var plotSynopsis:TextView? = null
    var userRating:TextView? = null
    var releaseDate:TextView? = null
    var imageView: ImageView? = null
    var movie: Movie? = null
    var thumbnail: String? = null
    var movieName:kotlin.String? = null
    var synopsis:kotlin.String? = null
    var rating:kotlin.String? = null
    var dateOfRelease:kotlin.String? = null
    var movie_id = 0
    private val recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var intent=getIntent();



        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        imageView = findViewById(R.id.thumbnail_image_header);
        nameOfMovie =  findViewById(R.id.title);
        plotSynopsis = findViewById(R.id.plotsynopsis);
        userRating = findViewById(R.id.userrating);
        releaseDate = findViewById(R.id.releasedate);

        thumbnail = intent.getStringExtra("poster")
        movieName = intent.getStringExtra("title")
        synopsis =  intent.getStringExtra("synopsis")
        rating =   intent.getDoubleExtra("rating",0.0).toString()
        dateOfRelease = intent.getStringExtra("release")
        movie_id = intent.getIntExtra("id",-1)

        val poster = "https://image.tmdb.org/t/p/w500$thumbnail"

        Glide.with(this)
            .load(poster)
            .placeholder(R.drawable.load)
            .into(imageView)

        nameOfMovie!!.setText(movieName)
        plotSynopsis!!.setText(synopsis)
        userRating!!.setText(rating)
        releaseDate!!.setText(dateOfRelease)

    }
}