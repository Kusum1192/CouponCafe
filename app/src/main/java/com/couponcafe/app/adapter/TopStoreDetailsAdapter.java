package com.couponcafe.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.models.BestOfferDatum;
import com.couponcafe.app.models.OffersDatum;
import com.couponcafe.app.models.StoreDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TopStoreDetailsAdapter extends RecyclerView.Adapter<TopStoreDetailsAdapter.ViewHolder> {
    ArrayList<OffersDatum> items;
    Context context;

    public TopStoreDetailsAdapter(ArrayList<OffersDatum> bestOfferDatum, Context context) {
        this.context = context;
        this.items = bestOfferDatum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_list_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.offername.setText(items.get(position).getOfferName());
        String truncateString = items.get(position).getShortDescription();
        if(truncateString.length()<85) holder.description.setText(items.get(position).getShortDescription());
        else  holder.description.setText(truncateString.substring(0,85));

        holder.offercashback.setText(items.get(position).getCashBack());
        holder.offercategory.setText(items.get(position).getCategory());

        Picasso.get().load(items.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((holder.offerImage));
        holder.tv_success.setText(items.get(position).getSuces()+"% Success");
        holder.tv_user_visit.setText(items.get(position).getUsrs()+" Users Today");
    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView offername,offercategory,description,offercashback,tv_success,tv_user_visit;
        ImageView offerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            offername = itemView.findViewById(R.id.offer_name);
            offercategory = itemView.findViewById(R.id.offer_category);
            description = itemView.findViewById(R.id.offer_short_description);
            offercashback = itemView.findViewById(R.id.offer_cashback);
            offerImage = itemView.findViewById(R.id.offer_image);
            tv_success = itemView.findViewById(R.id.tv_success);
            tv_user_visit = itemView.findViewById(R.id.tv_user_visit);

        }

    }


}