package com.couponcafe.app.testing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.models.CategoryDatum;


import java.util.ArrayList;


public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {

    ArrayList<Integer> counter = new ArrayList<Integer>();
    ArrayList<CategoryDatum> categoryDatumArrayList;
    Context context;



    public ExpandableRecyclerViewAdapter(FragmentActivity activity, ArrayList<CategoryDatum> categoryDatumArrayList) {
        this.categoryDatumArrayList = categoryDatumArrayList;
        this.context = activity;
        for (int i = 0; i < categoryDatumArrayList.size(); i++) {
            counter.add(0);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,tv_movie_category;
        ImageButton dropBtn;
        RecyclerView cardRecyclerView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryTitle);
            tv_movie_category = itemView.findViewById(R.id.tv_movie_category);
            dropBtn = itemView.findViewById(R.id.categoryExpandBtn);
            cardRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_collapseview, parent, false);

        ExpandableRecyclerViewAdapter.ViewHolder vh = new ExpandableRecyclerViewAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_movie_category.setText(categoryDatumArrayList.get(position).getCategoryName());
        InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(categoryDatumArrayList.get(position).getSubCategories(), context);
        holder.cardRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter.get(position) % 2 == 0) {
                    holder.cardRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.cardRecyclerView.setVisibility(View.GONE);
                }

                counter.set(position, counter.get(position) + 1);

            }
        });
        holder.cardRecyclerView.setAdapter(itemInnerRecyclerView);

    }

    @Override
    public int getItemCount() {
        return categoryDatumArrayList.size();
    }


}
