package com.example.movieapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapplication.R;
import com.example.movieapplication.model.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.MovieCastHolder> {
    private final List<Cast> castList;
    private final Context context;


    public MovieCastAdapter(List<Cast> castList, Context context) {
        this.castList = castList;
        this.context = context;
    }


    @NonNull
    @Override
    public MovieCastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cast, parent, false);
        return new MovieCastAdapter.MovieCastHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MovieCastHolder holder, final int position) {
        Cast cast = castList.get(position);
        holder.tvCastTitle.setText(cast.getName());
        if (cast.getProfile_path() != null) {
            Picasso.get().
                    load(cast.getProfile_path())
                    .placeholder(R.drawable.loading_black)
                    .into(holder.ivCastPoster);
        } else {
            Picasso.get().
                    load("https://www.salonlfc.com/wp-content/uploads/2018/01/image-not-found-scaled-1150x647.png")
                    .placeholder(R.drawable.loading_black)
                    .into(holder.ivCastPoster);
        }
    }


    @Override
    public int getItemCount() {

        return castList.size();

    }


    public static class MovieCastHolder extends RecyclerView.ViewHolder {

        private final TextView tvCastTitle;
        private final ImageView ivCastPoster;

        public MovieCastHolder(View itemView) {
            super(itemView);
            tvCastTitle = itemView.findViewById(R.id.tvCastTitle);
            ivCastPoster = itemView.findViewById(R.id.ivCastPoster);
        }
    }
}
