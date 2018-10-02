package com.buildbrothers.popularmovies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast

import com.buildbrothers.popularmovies.utils.MoviesJsonUtils
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler

import java.util.Arrays

import cz.msebera.android.httpclient.Header

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var moviesAdapter: MoviesAdapter? = null

    private var progressBar: ProgressBar? = null

    //gets settings up and running
    // Register the listener so latest settings can take effect instantly
    private val sortType: String
        get() {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val sortType = sharedPreferences.getString(getString(R.string.pref_sort_key),
                    getString(R.string.popularValue))
            sharedPreferences.registerOnSharedPreferenceChangeListener(this)

            return sortType!!
        }

    /* checks if network connection is on */
    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.isConnected
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress)

        val fullUrl = API_URL + sortType
        requestMovies(fullUrl)

    }

    /*sends request for data from moviedb */
    private fun requestMovies(url: String) {
        val client = AsyncHttpClient()
        val params = RequestParams().apply{
            put("api_key", API_KEY)
        }

        if (isNetworkAvailable) {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_LONG).show()
            return
        }

        progressBar!!.visibility = ProgressBar.VISIBLE

        client.post(url, params, object : TextHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, res: String) {
                progressBar!!.visibility = ProgressBar.GONE
                moviesAdapter = MoviesAdapter(this@MainActivity, MoviesJsonUtils.parseMoviesJson(res)!!.asList())

                val gridView = findViewById<GridView>(R.id.movies_grid)
                gridView.adapter = moviesAdapter

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, res: String, t: Throwable) {
                progressBar!!.visibility = ProgressBar.GONE
            }
        })
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == getString(R.string.pref_sort_key)) {
            val sortType = sharedPreferences.getString(getString(R.string.pref_sort_key),
                    getString(R.string.popularValue))
            val url = API_URL + sortType!!
            requestMovies(url)
        }

    }

    override fun onDestroy() {
        // Unregister OnPreferenceChangedListener to avoid any memory leaks
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this)

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == R.id.settings) {
            val i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)

    }

    companion object {
        //TODO(" Place your APY Key below")
        const val API_KEY = ""
        const val API_URL = "http://api.themoviedb.org/3/movie/"
    }
}
