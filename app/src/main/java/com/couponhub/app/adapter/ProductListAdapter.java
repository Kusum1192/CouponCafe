package com.couponhub.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;
import com.couponhub.app.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    ArrayList<Product> items;
    Context context;

    public ProductListAdapter(ArrayList<Product> bestOfferDatum, Context context) {
        this.context = context;
        this.items = bestOfferDatum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_product_name.setText(items.get(position).getProductName());
        holder.tv_description.setText(items.get(position).getShortDescription());
        holder.tv_product_price.setText(items.get(position).getProductPrice());

        Picasso.get().load(items.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((holder.image_view_product));

    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_product_name,tv_product_price,tv_description;
        ImageView image_view_product;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tv_description = itemView.findViewById(R.id.tv_description);
            image_view_product = itemView.findViewById(R.id.image_view_product);

        }

    }


}