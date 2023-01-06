package com.couponhub.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.R;
import com.couponhub.app.models.Transaction;
import com.couponhub.app.utils.Constants;

import java.util.ArrayList;


public class UserTransactionsListAdapter extends RecyclerView.Adapter<UserTransactionsListAdapter.ViewHolder> {
    ArrayList<Transaction> transactionArrayList;
    Context context;

    public UserTransactionsListAdapter(ArrayList<Transaction> transactionArrayList, Context context) {
        this.context = context;
        this.transactionArrayList = transactionArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_transaction_item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


            holder.tv_title.setText(transactionArrayList.get(position).getTransName());
            holder.tv_amount.setText(Constants.getSharedPreferenceString(context, "currency", "") + " " + transactionArrayList.get(position).getAmount());
            holder.tv_date.setText(transactionArrayList.get(position).getTransDate());
            holder.tv_time.setText(transactionArrayList.get(position).getTransTime());



    }

    @Override
    public int getItemCount() {
        if(transactionArrayList!=null){
            return transactionArrayList.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title,tv_amount,tv_date,tv_time;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);


        }

    }


}