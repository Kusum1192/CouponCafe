package com.couponhub.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;
import com.couponhub.app.models.Notification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {
    ArrayList<Notification> items;
    Context context;

    public NotificationsListAdapter(ArrayList<Notification> bestOfferDatum, Context context) {
        this.context = context;
        this.items = bestOfferDatum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notfication_item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_notification_name.setText(items.get(position).getNotifyTitle());
        holder.tv_notification_description.setText(items.get(position).getShortDescription());
        holder.tv_notification_tym.setText(items.get(position).getTimesAgo());

        Picasso.get().load(items.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((holder.image_view_notification));

    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_notification_name,tv_notification_description,tv_notification_tym;
        ImageView image_view_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_notification_name = itemView.findViewById(R.id.tv_notification_name);
            tv_notification_description = itemView.findViewById(R.id.tv_notification_description);
            tv_notification_tym = itemView.findViewById(R.id.tv_notification_tym);
            image_view_notification = itemView.findViewById(R.id.image_view_notification);

        }

    }


}