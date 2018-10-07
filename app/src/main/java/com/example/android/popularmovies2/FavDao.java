package com.example.android.popularmovies2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.popularmovies2.Database.FavoriteEntityy;

@Dao
public interface FavDao {

    @Query("SELECT * from favorite ORDER By `favorite-movie-name`")
     LiveData<FavoriteEntityy> loadAllTasks();

    @Insert
    void insertTask(FavoriteEntityy favoriteEntityy);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(FavoriteEntityy favoriteEntityy);

    @Delete
    void deleteTask(FavoriteEntityy favoriteEntityy);

    @Query("SELECT * from favorite WHERE id = :id")
    LiveData<FavoriteEntityy> loadTaskById(int id);
}
