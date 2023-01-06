package com.couponhub.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;
import com.couponhub.app.models.TopStoreDatum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopStoresAdapter extends RecyclerView.Adapter<TopStoresAdapter.MyViewHolder> {
 
    private ArrayList<TopStoreDatum> moviesList;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView imageView_topstore;
 
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            imageView_topstore = (ImageView) view.findViewById(R.id.imageView_topstore);
        }
    }
    public TopStoresAdapter(ArrayList<TopStoreDatum> moviesList) {
        this.moviesList = moviesList;
    }
 
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TopStoreDatum topStores = moviesList.get(position);
        holder.title.setText(topStores.getStoreName());
        holder.genre.setText(topStores.getCashBack());
        Picasso.get().load(topStores.getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((holder.imageView_topstore));
    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}