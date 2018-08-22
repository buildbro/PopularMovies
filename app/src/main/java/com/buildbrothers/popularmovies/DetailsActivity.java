package com.buildbrothers.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.original_title)
    TextView mTitleTv;
    @BindView(R.id.overview)
    TextView mOverviewTv;
    @BindView(R.id.rating)
    TextView mRatingTv;
    @BindView(R.id.release)
    TextView mReleaseDateTv;

    public static final String DATE_INPUT_FORMAT = "yyyy-MM-dd";
    public static final String DATE_OUTPUT_FORMAT = "MMM dd, yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Movies movies = intent.getParcelableExtra(MoviesAdapter.EXTRA_MOVIE);
            String title = movies.getOriginalTitle();
            String overview = movies.getOverview();
            String imageUrl = movies.getImage();
            String releaseDate = movies.getReleaseDate();
            String rating = movies.getVoteAverage();

            mTitleTv.setText(title);
            mOverviewTv.setText(overview);

            ImageView moviePoster = findViewById(R.id.image);
            Picasso.with(this)
                    .load(imageUrl)
                    .into(moviePoster);
            mRatingTv.setText(rating+"/10");

            DateFormat dateFormat = new SimpleDateFormat(DATE_INPUT_FORMAT);
            Date date = new Date();
            try {
                date = dateFormat.parse(releaseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat finalFormat=new SimpleDateFormat(DATE_OUTPUT_FORMAT);
            String monthYear =finalFormat.format(date);

            mReleaseDateTv.setText(monthYear);
        }
    }
}
