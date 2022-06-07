package com.example.movieapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapplication.ui.MovieDetailActivity;
import com.example.movieapplication.R;
import com.example.movieapplication.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PopularMovieHolder> {

    private final List<Movie> popularMovieList;
    private final Context context;
    public MovieAdapter(List<Movie> popularMovieList, Context context) {
        this.popularMovieList = popularMovieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.PopularMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_nowplaying_movie, parent, false);
        return new PopularMovieHolder(view);
    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularMovieHolder holder, final int position) {
        holder.tvPopularMovieTitle.setText(popularMovieList.get(position).getTitle());
        Picasso.get().load(popularMovieList.get(position).getPoster_path()).placeholder(R.drawable.loading_black).into(holder.ivPopularPoster);
    }


    public class PopularMovieHolder extends RecyclerView.ViewHolder {
        public TextView tvPopularMovieTitle;
        public ImageView ivPopularPoster;

        public PopularMovieHolder(View itemView) {
            super(itemView);
            tvPopularMovieTitle = itemView.findViewById(R.id.tvPopularMovieTitle);
            ivPopularPoster = itemView.findViewById(R.id.ivPopularPoster);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    Movie selectedMovie = popularMovieList.get(pos);
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("id", selectedMovie.getId());
                    intent.putExtra("title", selectedMovie.getTitle());
                    intent.putExtra("backdrop", selectedMovie.getBackdrop_path());
                    intent.putExtra("poster", selectedMovie.getPoster_path());
                    intent.putExtra("overview", selectedMovie.getOverview());
                    intent.putExtra("popularity", selectedMovie.getPopularity());
                    intent.putExtra("vote_count", selectedMovie.getVote_count());
                    intent.putExtra("vote_average", selectedMovie.getVote_average());
                    intent.putExtra("original_lang", selectedMovie.getOriginal_language());
                    intent.putExtra("release_date", selectedMovie.getRelease_date());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
