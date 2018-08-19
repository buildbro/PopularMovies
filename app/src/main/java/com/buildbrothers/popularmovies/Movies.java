package com.buildbrothers.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    String originalTitle;
    String id;
    String overview;
    String image;
    String voteAverage;
    String releaseDate;

    public Movies(String originalTitle, String id, String overview, String image, String voteAverage, String releaseDate) {
        this.originalTitle = originalTitle;
        this.id = id;
        this.overview = overview;
        this.image = image;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public Movies(Parcel in) {
        originalTitle = in.readString();
        id = in.readString();
        overview = in.readString();
        image = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();

    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(originalTitle);
        dest.writeString(id);
        dest.writeString(overview);
        dest.writeString(image);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
    }

}
