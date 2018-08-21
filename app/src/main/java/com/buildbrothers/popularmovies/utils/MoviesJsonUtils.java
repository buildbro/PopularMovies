package com.buildbrothers.popularmovies.utils;

import android.util.Log;

import com.buildbrothers.popularmovies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesJsonUtils {
    private static final String TAG = "MoviesJsonUtils";

    public static final String RESULT_FIELD = "results";
    public static final String  TITLE_FIELD = "original_title";
    public static final String  ID_FIELD = "id";
    public static final String OVERVIEW_FIELD = "overview";
    public static final String IMAGE_FIELD = "poster_path";
    public static final String VOTE_AVERAGE_FIELD = "vote_average";
    public static final String RELEASE_DATE = "release_date";


    public static Movies[] parseMoviesJson(String json) {
        if (json==null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray moviesJsonArray = jsonObject.optJSONArray(RESULT_FIELD);

            Movies[] moviesArray = new Movies[moviesJsonArray.length()];
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                String originalTitle = moviesJsonArray.optJSONObject(i).optString(TITLE_FIELD);
                String id = moviesJsonArray.optJSONObject(i).optString(ID_FIELD);
                String overview = moviesJsonArray.optJSONObject(i).optString(OVERVIEW_FIELD);
                String image = "http://image.tmdb.org/t/p/w185//" + moviesJsonArray.optJSONObject(i).optString(IMAGE_FIELD);
                String voteAverage = moviesJsonArray.optJSONObject(i).optString(VOTE_AVERAGE_FIELD);
                String releaseDate = moviesJsonArray.optJSONObject(i).optString(RELEASE_DATE);

                moviesArray[i] = new Movies(originalTitle, id, overview, image, voteAverage, releaseDate);
            }

            return moviesArray;

        }catch (JSONException e) {
            Log.e(TAG, "JSON error: " + e);
            return null;
        }
    }
}
