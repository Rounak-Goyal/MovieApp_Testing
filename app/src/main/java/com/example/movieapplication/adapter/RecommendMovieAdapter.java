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

import com.example.movieapplication.R;
import com.example.movieapplication.model.Movie;
import com.example.movieapplication.ui.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecommendMovieAdapter extends RecyclerView.Adapter<RecommendMovieAdapter.RecommendMovieHolder> {


    private final List<Movie> popularMovieList;
    private final Context context;
    public RecommendMovieAdapter(List<Movie> popularMovieList, Context context) {
        this.popularMovieList = popularMovieList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecommendMovieAdapter.RecommendMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recommend_movie, parent, false);
        return new RecommendMovieAdapter.RecommendMovieHolder(view);
    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecommendMovieAdapter.RecommendMovieHolder holder, final int position) {
        holder.tvRecommendMovieTitle.setText(popularMovieList.get(position).getTitle());
        Picasso.get().load(popularMovieList.get(position).getPoster_path()).placeholder(R.drawable.loading_black).into(holder.ivRecommendMoviePoster);
    }


    public class RecommendMovieHolder extends RecyclerView.ViewHolder {

        private final TextView tvRecommendMovieTitle;
        private final ImageView ivRecommendMoviePoster;

        public RecommendMovieHolder(View itemView) {
            super(itemView);
            tvRecommendMovieTitle = itemView.findViewById(R.id.tvRecommendMovieTitle);
            ivRecommendMoviePoster = itemView.findViewById(R.id.ivRecommendMoviePoster);

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
                    Toast.makeText(context,"Detailed Description of "+selectedMovie.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
