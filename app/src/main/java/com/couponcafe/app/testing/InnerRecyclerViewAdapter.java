package com.couponcafe.app.testing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.couponcafe.app.R;
import com.couponcafe.app.models.SubCategory;
import java.util.ArrayList;



public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {

    ArrayList<SubCategory> getofferList;
    Context context;


    public InnerRecyclerViewAdapter(ArrayList<SubCategory> getNameList, Context context) {
        this.getofferList = getNameList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_subcat_name,tv_subcat_offers;
        CardView cardView_child;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_subcat_name = itemView.findViewById(R.id.tv_subcat_name);
            tv_subcat_offers = itemView.findViewById(R.id.tv_subcat_offers);
            cardView_child = itemView.findViewById(R.id.cardView_child);
            cardView_child.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cardView_child:
                    Toast.makeText(context, "click child: "+getofferList.get(getAdapterPosition()).getSubCategoryName(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_expand_item_view, parent, false);


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
