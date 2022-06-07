package com.example.movieapplication.ui;

import static com.example.movieapplication.api.Service.IMAGE_BASE_URL_1280;
import static com.example.movieapplication.api.Service.IMAGE_BASE_URL_500;

import com.example.movieapplication.BuildConfig;
import com.example.movieapplication.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.movieapplication.adapter.MovieAdapter;
import com.example.movieapplication.adapter.MovieCastAdapter;
import com.example.movieapplication.adapter.RecommendMovieAdapter;
import com.example.movieapplication.api.Client;
import com.example.movieapplication.api.Service;
import com.example.movieapplication.model.Cast;
import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.ResponseClip;
import com.example.movieapplication.model.ResponseCreditDetail;
import com.example.movieapplication.model.ResponseNowPlaying;
import com.example.movieapplication.model.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    String title;
    int id;
    ImageView ivHorizontalPoster, ivVerticalPoster;
    TextView tvTitle, tvPopularity, tvReleaseDate, tvOverview,tvViews,tvRating,tvLanguage;
    VideoView v;
    static String key = "qDd3eAuFZjM";
    private YouTubePlayer mYoutubeView;

    public RecyclerView rvCast, rvRecommendContents;
    public RecyclerView.Adapter castAdapter, recommendAdapter;
    public RecyclerView.LayoutManager castLayoutManager, recommendLayoutManager;
    public List<Cast> castDataList;
    public List<Movie> recommendDataList;


    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
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


        castDataList = new ArrayList<>();
        castAdapter = new MovieCastAdapter(castDataList, this);
        castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCast = findViewById(R.id.rvCast);
        rvCast.setHasFixedSize(true);
        rvCast.setLayoutManager(castLayoutManager);
        rvCast.setAdapter(castAdapter);

        recommendDataList = new ArrayList<>();
        recommendAdapter = new RecommendMovieAdapter(recommendDataList, this);
        recommendLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRecommendContents = findViewById(R.id.rvRecommendContents);
        rvRecommendContents.setHasFixedSize(true);
        rvRecommendContents.setLayoutManager(recommendLayoutManager);
        rvRecommendContents.setAdapter(recommendAdapter);

        loadJSON();
        recommendAdapter.notifyDataSetChanged();
        castAdapter.notifyDataSetChanged();

        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.cueVideo(key);
                mYoutubeView = youTubePlayer;
                //Toast.makeText(getApplicationContext(),"Play video!!"+ key,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),"Unable to Play video!!",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void loadJSON() {
        try {
            String API_KEY = BuildConfig.API_KEY;

            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<ResponseCreditDetail> call_cast = apiService.getCreditDetail(id,API_KEY);
            call_cast.enqueue(new Callback<ResponseCreditDetail>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCreditDetail> call, @NonNull Response<ResponseCreditDetail> response) {
                    List<Cast> cast = response.body().getCast();

                    rvCast.setAdapter(new MovieCastAdapter(cast, getApplicationContext()));
                    rvCast.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseCreditDetail> call, @NonNull Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Error in fetching results!!",Toast.LENGTH_SHORT).show();
                }
            });

            Call<ResponseNowPlaying> call_recommend = apiService.getRecommendDetail(id,API_KEY);
            call_recommend.enqueue(new Callback<ResponseNowPlaying>() {
                @Override
                public void onResponse(@NonNull Call<ResponseNowPlaying> call, @NonNull Response<ResponseNowPlaying> response) {
                    assert response.body() != null;
                    List<Movie> movies = response.body().getResults();

                    rvRecommendContents.setAdapter(new MovieAdapter(movies, getApplicationContext()));
                    rvRecommendContents.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseNowPlaying> call, @NonNull Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Error in fetching results!!",Toast.LENGTH_SHORT).show();
                }
            });

            Call<ResponseClip> call_video = apiService.getClips(id,API_KEY);
            call_video.enqueue(new Callback<ResponseClip>() {
                @Override
                public void onResponse(@NonNull Call<ResponseClip> call, @NonNull Response<ResponseClip> response) {
                    assert response.body() != null;
                    List<Video> video = response.body().getVideo();
                    key = video.get(0).getKey();
                    Toast.makeText(getApplicationContext(),"fetching results!!  "+key,Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(@NonNull Call<ResponseClip> call, @NonNull Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Error in fetching results!!",Toast.LENGTH_SHORT).show();
                }
            });




        }catch (Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

}