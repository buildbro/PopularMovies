package com.buildbrothers.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildbrothers.popularmovies.utils.MoviesJsonUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String API_KEY = "";
    public static final String API_URL = "http://api.themoviedb.org/3/movie/";

    private MoviesAdapter moviesAdapter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);

        String fullUrl = API_URL + getSortType();
        requestMovies(fullUrl);

    }

    /*sends request for data from moviedb */
    public void requestMovies(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY);
        if (isNetworkAvailable()) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String res) {
                    progressBar.setVisibility(ProgressBar.GONE);
                    moviesAdapter = new MoviesAdapter(MainActivity.this, Arrays.asList(MoviesJsonUtils.parseMoviesJson(res)));

                    GridView gridView = findViewById(R.id.movies_grid);
                    gridView.setAdapter(moviesAdapter);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            });
        } else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_LONG).show();
        }
    }

    //gets settings up and running
    public String getSortType() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortType = sharedPreferences.getString(getString(R.string.pref_sort_key),
                getString(R.string.popularValue));

        // Register the listener so latest settings can take effect instantly
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        return sortType;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_key))) {
            String sortType = sharedPreferences.getString(getString(R.string.pref_sort_key),
                    getString(R.string.popularValue));
            String url = API_URL + sortType;
            requestMovies(url);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister OnPreferenceChangedListener to avoid any memory leaks
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);

    }

    /* checks if network connection is on */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.isConnected();
        }
        return false;
    }
}
