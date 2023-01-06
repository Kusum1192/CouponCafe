package com.couponhub.app.testing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;

import com.couponhub.app.activities.CategoriesDetailsActivity;
import com.couponhub.app.models.CategoryDatum;
import com.squareup.picasso.Picasso;


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
        TextView tv_movie_category,tv_movie_subcategories;
//        ImageButton dropBtn;
        RecyclerView cardRecyclerView;
        CardView cardView;
        ImageView iv_product_image;
        LinearLayout ll_view_all;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product_image = itemView.findViewById(R.id.iv_product_image);
            tv_movie_category = itemView.findViewById(R.id.tv_movie_category);
            tv_movie_subcategories = itemView.findViewById(R.id.tv_movie_subcategories);
//            dropBtn = itemView.findViewById(R.id.categoryExpandBtn);
            cardRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
            ll_view_all = itemView.findViewById(R.id.ll_view_all);
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
        Picasso.get().load(categoryDatumArrayList.get(position).getImageUrl()).placeholder(R.drawable.ic_placeholder_small).error(R.drawable.ic_placeholder_small).into((holder.iv_product_image));
        holder.tv_movie_category.setText(categoryDatumArrayList.get(position).getCategoryName());
        holder.tv_movie_subcategories.setText(categoryDatumArrayList.get(position).getOffersCount());
        InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(categoryDatumArrayList.get(position).getCategoryId(),categoryDatumArrayList.get(position).getSubCategories(), context);
        holder.cardRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        holder.ll_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoriesDetailsActivity.class);
//                intent.putExtra("subcatId",getofferList.get(getAdapterPosition()).getSubCategoryId());
//                intent.putExtra("subcatName",getofferList.get(getAdapterPosition()).getSubCategoryName());
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter.get(position) % 2 == 0) {

                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            holder.cardRecyclerView.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    holder.cardRecyclerView.startAnimation(animate);
                    holder.ll_view_all.startAnimation(animate);
                    holder.cardRecyclerView.setVisibility(View.VISIBLE);
                    holder.ll_view_all.setVisibility(View.VISIBLE);
                } else {

                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            0,
                            holder.cardRecyclerView.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    holder.cardRecyclerView.startAnimation(animate);
                    holder.ll_view_all.startAnimation(animate);
                    holder.cardRecyclerView.setVisibility(View.GONE);
                    holder.ll_view_all.setVisibility(View.GONE);
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
