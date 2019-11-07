package com.buildbrothers.popularmovies

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.squareup.picasso.Picasso

class MoviesAdapter(context: Activity, movies: List<Movies>) : ArrayAdapter<Movies>(context, 0, movies) {


    override fun getView(position: Int, mConvertView: View?, parent: ViewGroup): View {
        var convertView = mConvertView
        val movies = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        }

        if (movies != null) {
            val moviePoster = convertView!!.findViewById<ImageView>(R.id.image)
            Picasso.with(context)
                    .load(movies.image)
                    .into(moviePoster)
            val movieTitle = convertView.findViewById<TextView>(R.id.title)
            movieTitle.text = movies.originalTitle
            moviePoster.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, movies)
                context.startActivity(intent)
            }
        } else {
            Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
        }

        return convertView!!
    }

    companion object {

        const val EXTRA_MOVIE = "movie_key"
    }
}
