package com.example.movieapplication.ui;

import static com.example.movieapplication.api.Service.IMAGE_BASE_URL_1280;
import static com.example.movieapplication.api.Service.IMAGE_BASE_URL_500;

import com.example.movieapplication.R;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    String title;
    int id;
    ImageView ivHorizontalPoster, ivVerticalPoster;
    TextView tvTitle, tvPopularity, tvReleaseDate, tvOverview,tvViews,tvRating,tvLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ivVerticalPoster = findViewById(R.id.ivVerticalPoster);
        ivHorizontalPoster = findViewById(R.id.ivHorizontalPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvRating= findViewById(R.id.tvRating);
        tvViews= findViewById(R.id.tvViews);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvOverview = findViewById(R.id.tvOverView);

        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        tvTitle.setText(title);
        tvPopularity.setText("Popularity : " + (getIntent().getDoubleExtra("popularity", 0)));
        tvViews.setText("Views : " + (getIntent().getIntExtra("vote_count", 0)));
        tvRating.setText("Rating : " + (getIntent().getDoubleExtra("vote_average", 0)));
        tvReleaseDate.setText("Release Date : " + getIntent().getStringExtra("release_date"));
        tvLanguage.setText("Language : " + getIntent().getStringExtra("original_lang"));
        Picasso.get().load(IMAGE_BASE_URL_1280 + getIntent().getStringExtra("backdrop")).into(ivHorizontalPoster);
        Picasso.get().load(IMAGE_BASE_URL_500 + getIntent().getStringExtra("poster")).into(ivVerticalPoster);

        tvOverview.setText(getIntent().getStringExtra("overview"));






    }
}