package com.couponcafe.app.testing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.couponcafe.app.R;

import com.couponcafe.app.activities.CategoriesDetailsActivity;
import com.couponcafe.app.models.CategoryDatum;
import com.couponcafe.app.models.SubCategory;
import java.util.ArrayList;



public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {

    ArrayList<SubCategory> getofferList;
    Integer categoryId;
    Context context;


    public InnerRecyclerViewAdapter(Integer categoryId, ArrayList<SubCategory> getNameList, Context context) {
        this.getofferList = getNameList;
        this.categoryId = categoryId;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subcat_name,tv_subcat_offers;
        LinearLayout cardView_child;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_subcat_name = itemView.findViewById(R.id.tv_subcat_name);
            tv_subcat_offers = itemView.findViewById(R.id.tv_subcat_offers);
            cardView_child = itemView.findViewById(R.id.cardView_child);
            //ll_view_all = itemView.findViewById(R.id.ll_view_all);
            cardView_child.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cardView_child:
                    Intent intent = new Intent(context, CategoriesDetailsActivity.class);
                    intent.putExtra("catId",categoryId);
                    intent.putExtra("subcatId",getofferList.get(getAdapterPosition()).getSubCategoryId());
                    intent.putExtra("subcatName",getofferList.get(getAdapterPosition()).getSubCategoryName());
                    context.startActivity(intent);
                    //Toast.makeText(context, "click child: "+getofferList.get(getAdapterPosition()).getSubCategoryId(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_view, parent, false);

        InnerRecyclerViewAdapter.ViewHolder vh = new InnerRecyclerViewAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_subcat_name.setText(getofferList.get(position).getSubCategoryName());
        holder.tv_subcat_offers.setText(getofferList.get(position).getOffersCount());

    }

    @Override
    public int getItemCount() {
        return getofferList.size();
    }


}
