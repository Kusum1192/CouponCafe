package com.couponcafe.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.models.InvitedUser;
import com.couponcafe.app.models.RecentUser;
import com.couponcafe.app.utils.CircleTransform;
import com.couponcafe.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecentUserAdapter extends RecyclerView.Adapter<RecentUserAdapter.ViewHolder> {
    ArrayList<RecentUser> recentUserArrayList;
    Context context;

    public RecentUserAdapter(ArrayList<RecentUser> recentUserArrayList, Context context) {
        this.context = context;
        this.recentUserArrayList = recentUserArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_user_list_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_username.setText(recentUserArrayList.get(position).getUserName());
        holder.tv_date.setText(recentUserArrayList.get(position).getDate());
        holder.tv_work.setText(recentUserArrayList.get(position).getShowText());
        Picasso.get().load(recentUserArrayList.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small).transform(new CircleTransform())
                .into((holder.iv_user_profile));

    }

    @Override
    public int getItemCount() {
        if(recentUserArrayList!=null){
            return recentUserArrayList.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_username,tv_work,tv_date;
        ImageView iv_user_profile;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_work = itemView.findViewById(R.id.tv_work);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_user_profile = itemView.findViewById(R.id.iv_user_profile);

        }

    }


}