package com.buildbrothers.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movies> {

    public static final String EXTRA_MOVIE = "movie_key";

    public  MoviesAdapter(Activity context, List<Movies> movies) {
        super(context, 0, movies);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Movies movies = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        if (movies != null) {
            ImageView moviePoster = convertView.findViewById(R.id.image);
            Picasso.with(getContext())
                    .load(movies.image)
                    .into(moviePoster);
            TextView movieTitle = convertView.findViewById(R.id.title);
            movieTitle.setText(movies.originalTitle);
            moviePoster.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   Intent intent = new Intent(getContext(), DetailsActivity.class);
                                                   intent.putExtra(EXTRA_MOVIE, movies);
                                                   getContext().startActivity(intent);
                                               }
                                           }
            );
        } else {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }
}
