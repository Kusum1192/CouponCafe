package com.couponcafe.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.R;
import com.couponcafe.app.models.InviteFriendModel;
import com.couponcafe.app.models.InvitedUser;
import com.couponcafe.app.models.OffersDatum;
import com.couponcafe.app.utils.CircleTransform;
import com.couponcafe.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class InviteUserAdapter extends RecyclerView.Adapter<InviteUserAdapter.ViewHolder> {
    ArrayList<InvitedUser> items;
    Context context;

    public InviteUserAdapter(ArrayList<InvitedUser> bestOfferDatum, Context context) {
        this.context = context;
        this.items = bestOfferDatum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invite_user_list_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_username.setText(items.get(position).getUserName());
        holder.tv_rupee.setText("Bonus: "+Constants.getSharedPreferenceString(context,"currency","")+" "+items.get(position).getAmount());
        holder.tv_date.setText(items.get(position).getDate());
        holder.tv_tym.setText(items.get(position).getTime());

        Picasso.get().load(items.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small).transform(new CircleTransform())
                .into((holder.iv_user_image));

    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_username,tv_rupee,tv_date,tv_tym;
        ImageView iv_user_image;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_rupee = itemView.findViewById(R.id.tv_rupees);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_tym = itemView.findViewById(R.id.tv_tym);
            iv_user_image = itemView.findViewById(R.id.iv_user_image);

        }

    }


}