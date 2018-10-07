package com.example.android.popularmovies2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.android.popularmovies2.Data.Contract;
import com.example.android.popularmovies2.Database.AddTaskViewModel;
import com.example.android.popularmovies2.Database.AddViewModelFactory;
import com.example.android.popularmovies2.Database.AppDatabase;
import com.example.android.popularmovies2.Database.FavoriteEntityy;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmovies2.Data.Contract.EXTRA_OVERVIEW;
import static com.example.android.popularmovies2.Data.Contract.EXTRA_RATE;
import static com.example.android.popularmovies2.Data.Contract.EXTRA_TITLE;
import static com.example.android.popularmovies2.Data.Contract.EXTRA_URL;
import static com.example.android.popularmovies2.Data.Contract.EXTRA_YEAR;
import static com.example.android.popularmovies2.Data.Contract.VIDEO_URL;


public class DetailMovieActivity extends AppCompatActivity {
    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private static final int DEFAULT_TASK_ID = -1;


    private int mTaskId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    //field to store the movie details
    private String mUrl;
    private String mTitle;
    public TextView mTitleTxt, mReleaseDate, mVoteAverage, mOverview;
    private CheckBox mCheckBox;
    private Button videoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie2);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mCheckBox.setText(R.string.action_favorites);
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                AddViewModelFactory factory = new AddViewModelFactory(mDb, mTaskId);

                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                viewModel.getTask().observe(this, new Observer<FavoriteEntityy>() {
                    @Override
                    public void onChanged(@Nullable FavoriteEntityy taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }

        }
            //Reference
            mTitleTxt = findViewById(R.id.original_title_tv);
            mReleaseDate = findViewById(R.id.release_date);
            mVoteAverage = findViewById(R.id.vote_average);
            mOverview = findViewById(R.id.overview);
            ImageView mMoviePoster = findViewById(R.id.movie_poster);
            RatingBar ratingBar = findViewById(R.id.ratingBar);
            mCheckBox = findViewById(R.id.favorites_checkbox);
            videoButton = findViewById(R.id.video_button);
            //when the checkbox is selected , add the movie to Favorite database
            onCheckboxClicked(mCheckBox);
            //videoView
            final VideoView videoView = (VideoView) findViewById(R.id.movie_video);
            //Creating MediaController
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            //specify the location of media file
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/media/1.mp4");

            //Setting MediaController and URI, then starting the videoView
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();

            Intent intentStartDetailActivity = getIntent();
            //get the intent
            Bundle bundle = getIntent().getExtras();
            if (bundle.getString(EXTRA_TITLE) != null) {
                mTitle = bundle.getString(EXTRA_TITLE);
                mTitleTxt.setText(mTitle);
            }
            if (bundle.getString(EXTRA_URL) != null) {
                mUrl = bundle.getString(EXTRA_URL);
            }
            if (bundle.getString(EXTRA_YEAR) != null) {
                mReleaseDate.setText(bundle.getString(EXTRA_YEAR));
            }
            if (bundle.getString(EXTRA_RATE) != null) {
                int number = Integer.parseInt(bundle.getString(EXTRA_RATE));
                float d = (float) ((number * 5) / 10);
                ratingBar.setRating(d);
            }
            if (bundle.getString(EXTRA_OVERVIEW) != null) {
                mOverview.setText(bundle.getString(EXTRA_OVERVIEW));
            }


            Picasso.with(this)
                    .load(Contract.IMAGE_URL + Contract.W780 + mUrl)
                    .into(mMoviePoster);
        }


        public void watchTrailerBtnClicked (View view){
            videoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //coding

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + VIDEO_URL)));
                    Log.i("Video", "Video Playing....");
                    Toast.makeText(DetailMovieActivity.this, "Watch the movie Trailer ", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
            MenuInflater inflater = getMenuInflater();
            /* Use the inflater's inflate method to inflate our menu layout to this menu */
            inflater.inflate(R.menu.main, menu);
            /* Return true so that the menu is displayed in the Toolbar */
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
            }
            if (id == R.id.favorites) {
                //open the favorites
                Intent favoritesIntent = new Intent(this, FavoritesActivity.class);
                startActivity(favoritesIntent);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public void onCheckboxClicked (View view){
            // Is the view now checked?
            boolean checked = ((CheckBox) view).isChecked();

            if (checked) {
                //coding...

            }


        }

        private void populateUI (FavoriteEntityy task){
            if (task == null) {
                return;
            }
        }
    }

