package com.buildbrothers.popularmovies

import android.os.Parcel
import android.os.Parcelable

class Movies : Parcelable {
    var originalTitle: String? = null
    var id: String? = null
    var overview: String? = null
    var image: String? = null
    var voteAverage: String? = null
    var releaseDate: String? = null

    constructor(originalTitle: String, id: String, overview: String, image: String, voteAverage: String, releaseDate: String) {
        this.originalTitle = originalTitle
        this.id = id
        this.overview = overview
        this.image = image
        this.voteAverage = voteAverage
        this.releaseDate = releaseDate
    }

    constructor(`in`: Parcel) {
        originalTitle = `in`.readString()
        id = `in`.readString()
        overview = `in`.readString()
        image = `in`.readString()
        voteAverage = `in`.readString()
        releaseDate = `in`.readString()

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, i: Int) {
        dest.writeString(originalTitle)
        dest.writeString(id)
        dest.writeString(overview)
        dest.writeString(image)
        dest.writeString(voteAverage)
        dest.writeString(releaseDate)
    }

    companion object CREATOR : Parcelable.Creator<Movies> {
        override fun createFromParcel(parcel: Parcel): Movies {
            return Movies(parcel)
        }

        override fun newArray(size: Int): Array<Movies?> {
            return arrayOfNulls(size)
        }
    }

}
