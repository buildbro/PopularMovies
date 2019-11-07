package com.buildbrothers.popularmovies.utils

import android.util.Log

import com.buildbrothers.popularmovies.Movies

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object MoviesJsonUtils {

    private const val TAG = "MoviesJsonUtils"

    private const val RESULT_FIELD = "results"
    private const val TITLE_FIELD = "original_title"
    private const val ID_FIELD = "id"
    private const val OVERVIEW_FIELD = "overview"
    private const val IMAGE_FIELD = "poster_path"
    private const val VOTE_AVERAGE_FIELD = "vote_average"
    private const val RELEASE_DATE = "release_date"


    fun parseMoviesJson(json: String?): Array<Movies>? {
        if (json == null) {
            return null
        }

        try {
            val jsonObject = JSONObject(json)

            val moviesJsonArray = jsonObject.optJSONArray(RESULT_FIELD)

            val moviesArray = emptyArray<Movies>()
            for (i in 0 until moviesJsonArray.length()) {
                val originalTitle = moviesJsonArray.optJSONObject(i).optString(TITLE_FIELD)
                val id = moviesJsonArray.optJSONObject(i).optString(ID_FIELD)
                val overview = moviesJsonArray.optJSONObject(i).optString(OVERVIEW_FIELD)
                val image = "http://image.tmdb.org/t/p/w185//" + moviesJsonArray.optJSONObject(i).optString(IMAGE_FIELD)
                val voteAverage = moviesJsonArray.optJSONObject(i).optString(VOTE_AVERAGE_FIELD)
                val releaseDate = moviesJsonArray.optJSONObject(i).optString(RELEASE_DATE)

                moviesArray[i] = Movies(originalTitle, id, overview, image, voteAverage, releaseDate)
            }

            return moviesArray

        } catch (e: JSONException) {
            Log.e(TAG, "JSON error: $e")
            return null
        }

    }
}
