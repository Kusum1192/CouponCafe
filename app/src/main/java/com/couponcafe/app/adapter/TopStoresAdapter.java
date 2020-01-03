package com.couponcafe.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;

import java.util.List;

public class TopStoresAdapter extends RecyclerView.Adapter<TopStoresAdapter.MyViewHolder> {
 
    private List<TopStores> moviesList;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
 
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }
 
 
    public TopStoresAdapter(List<TopStores> moviesList) {
        this.moviesList = moviesList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TopStores topStores = moviesList.get(position);
        holder.title.setText(topStores.getTitle());
        holder.genre.setText(topStores.getGenre());
        holder.year.setText(topStores.getYear());
    }
 
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}