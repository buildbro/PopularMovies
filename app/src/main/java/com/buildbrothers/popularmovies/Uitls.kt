package com.buildbrothers.popularmovies

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header


fun requestMovies(url: String){
    val client = AsyncHttpClient()
    val params = RequestParams().apply {
        put("api_key", "API_KEY_HERE")
    }

    // check your network
    if(false){
        return
    }

    //TODO("show progressBar")

    client.post(url, object: TextHttpResponseHandler() {
        override fun onSuccess(statusCode: Int,
                               headers: Array<out Header>?,
                               responseString: String?) {
            // Request success
            //TODO("hide progressBar")
            //TODO(" other code ")
        }

        override fun onFailure(statusCode: Int,
                               headers: Array<out Header>?,
                               responseString: String?,
                               throwable: Throwable?) {

            //TODO("hide progressBar")
            //TODO("show error")
        }
    })
}