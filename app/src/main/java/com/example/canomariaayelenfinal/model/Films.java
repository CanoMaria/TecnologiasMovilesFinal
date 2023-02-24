package com.example.canomariaayelenfinal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

public class Films implements Parcelable {

    private boolean adult;
    private String backdrop_path;
    private int[] genre_ids;
    private int id;
    private String original_language;
    private String original_title;
    private String synopsis;
    private String popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
    private String imageUrl;

    private boolean is_favourite;

    @Override
    public String toString() {
        return "Films{" +
                "adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genre_ids=" + Arrays.toString(genre_ids) +
                ", id=" + id +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", popularity='" + popularity + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", imageUrl='" + imageUrl + '\'' +
                ", is_favourite=" + is_favourite +
                '}';
    }

    public Films() {

    }



    public boolean getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(boolean is_favourite) {
        this.is_favourite = is_favourite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }


    protected Films(Parcel in) {
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        genre_ids = in.createIntArray();
        id = in.readInt();
        original_language = in.readString();
        original_title = in.readString();
        synopsis = in.readString();
        popularity = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        vote_count = in.readInt();
        imageUrl = in.readString();
        is_favourite = in.readByte() != 0;
    }

    public static final Creator<Films> CREATOR = new Creator<Films>() {
        @Override
        public Films createFromParcel(Parcel in) {
            return new Films(in);
        }

        @Override
        public Films[] newArray(int size) {
            return new Films[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(synopsis);
        dest.writeString(popularity);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (is_favourite ? 1 : 0));
    }
}
