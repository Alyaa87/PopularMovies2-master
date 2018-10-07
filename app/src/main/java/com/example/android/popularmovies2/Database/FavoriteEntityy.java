package com.example.android.popularmovies2.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "favorite")
public class FavoriteEntityy {
    //  Annotate the class with Entity. Use "task" for the table name
    //Annotate the id as PrimaryKey. Set autoGenerate to true.
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "favorite-movie-name")
    private String favoriteMovieName;


    @Ignore
    public FavoriteEntityy(String favoriteMovieName) {
        this.favoriteMovieName = favoriteMovieName;

    }

    public FavoriteEntityy(int id, String favoriteMovieName) {
        this.id = id;
        this.favoriteMovieName = favoriteMovieName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavoriteMovieName() {
        return favoriteMovieName;
    }

    public void setFavoriteMovieName(String favoriteMovieName) {
        this.favoriteMovieName = favoriteMovieName;
    }

}

