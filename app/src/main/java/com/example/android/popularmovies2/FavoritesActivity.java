package com.example.android.popularmovies2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.popularmovies2.Database.AddTaskViewModel;
import com.example.android.popularmovies2.Database.AddViewModelFactory;
import com.example.android.popularmovies2.Database.AppDatabase;
import com.example.android.popularmovies2.Database.FavoriteEntityy;

public class FavoritesActivity extends AppCompatActivity {
private TextView favoriteMovieName ,noMoviesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find all relevant views that we will need to read user input from
        favoriteMovieName = (TextView) findViewById(R.id.favorite_movie_tv);
        noMoviesTextView = (TextView)findViewById(R.id.no_movies_tv);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}



