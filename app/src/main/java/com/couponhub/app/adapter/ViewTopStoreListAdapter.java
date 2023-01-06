package com.couponhub.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;
import com.couponhub.app.models.TopStoreDatum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewTopStoreListAdapter extends RecyclerView.Adapter<ViewTopStoreListAdapter.ViewHolder> {
    ArrayList<TopStoreDatum> items;
    Context context;

    public ViewTopStoreListAdapter(ArrayList<TopStoreDatum> bestOfferDatum, Context context) {
        this.context = context;
        this.items = bestOfferDatum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_topstore_list_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_topstore_name.setText(items.get(position).getStoreName());
        holder.tv_cashback.setText(items.get(position).getCashBack());
        holder.tv_offer_count.setText(items.get(position).getOffersCount());
        holder.tv_topstore_category.setText(items.get(position).getCategory());

        Picasso.get().load(items.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((holder.image_view_topstore));

    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_topstore_name,tv_topstore_category,tv_cashback,tv_offer_count;
        ImageView image_view_topstore;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_topstore_name = itemView.findViewById(R.id.tv_topstore_name);
            tv_topstore_category = itemView.findViewById(R.id.tv_topstore_category);
            tv_cashback = itemView.findViewById(R.id.tv_cashback);
            tv_offer_count = itemView.findViewById(R.id.tv_offer_count);
            image_view_topstore = itemView.findViewById(R.id.image_view_topstore);

        }

    }


}