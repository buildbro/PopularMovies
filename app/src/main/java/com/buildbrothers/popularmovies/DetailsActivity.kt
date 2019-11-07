package com.buildbrothers.popularmovies

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife

class DetailsActivity : AppCompatActivity() {


    @BindView(R.id.original_title)
    internal var mTitleTv: TextView? = null
    @BindView(R.id.overview)
    internal var mOverviewTv: TextView? = null
    @BindView(R.id.rating)
    internal var mRatingTv: TextView? = null
    @BindView(R.id.release)
    internal var mReleaseDateTv: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        ButterKnife.bind(this)

        val intent = intent
        if (intent != null) {
            val movies = intent.getParcelableExtra<Movies>(MoviesAdapter.EXTRA_MOVIE)
            val title = movies.originalTitle
            val overview = movies.overview
            val imageUrl = movies.image
            val releaseDate = movies.releaseDate
            val rating = movies.voteAverage

            mTitleTv!!.text = title
            mOverviewTv!!.text = overview

            val moviePoster = findViewById<ImageView>(R.id.image)
            Picasso.with(this)
                    .load(imageUrl)
                    .into(moviePoster)
            mRatingTv!!.text = "$rating/10"

            val dateFormat = SimpleDateFormat(DATE_INPUT_FORMAT)
            var date = Date()
            try {
                date = dateFormat.parse(releaseDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val finalFormat = SimpleDateFormat(DATE_OUTPUT_FORMAT)
            val monthYear = finalFormat.format(date)

            mReleaseDateTv!!.text = monthYear
        }
    }

    companion object {

        const val DATE_INPUT_FORMAT = "yyyy-MM-dd"
        const val DATE_OUTPUT_FORMAT = "MMM dd, yyyy"
    }
}
