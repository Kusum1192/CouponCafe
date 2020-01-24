package com.couponcafe.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponcafe.app.MainActivity;
import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;
import com.couponcafe.app.activities.ProductDetailsActivity;
import com.couponcafe.app.adapter.ProductListAdapter;
import com.couponcafe.app.adapter.RecyclerTouchListener;
import com.couponcafe.app.models.Product;
import com.couponcafe.app.utils.Constants;

import java.util.ArrayList;


public class OverviewFragment extends Fragment implements View.OnClickListener {


    TextView textView_invite, tv_user_amount, tv_user_pending;
    Integer user_amount,user_pending;
    //LinearLayout ll_product_item;
    RecyclerView recyclerview_product;
    ProductListAdapter productListAdapter;
    ArrayList<Product> productlistarraylist;

    public OverviewFragment() {
        // Required empty public constructor

    }

    public OverviewFragment(Integer userAmount, Integer pendingAmount, ArrayList<Product> productlistarraylist) {
        this.user_amount = userAmount;
        this.user_pending = pendingAmount;
        this.productlistarraylist = productlistarraylist;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.overview_fragment, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        recyclerview_product = view.findViewById(R.id.recyclerview_product);
        textView_invite = view.findViewById(R.id.invite_now);
        tv_user_amount = view.findViewById(R.id.tv_user_amount);
        tv_user_pending = view.findViewById(R.id.tv_user_pending);
        tv_user_amount.setText(Constants.getSharedPreferenceString(getActivity(),"currency","")+" "+user_amount);
        tv_user_pending.setText(Constants.getSharedPreferenceString(getActivity(),"currency","")+""+user_pending);
        textView_invite.setOnClickListener(this);
       // ll_product_item.setOnClickListener(this);

        productListAdapter = new ProductListAdapter(productlistarraylist,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_product.setLayoutManager(mLayoutManager);
        recyclerview_product.setAdapter(productListAdapter);
        recyclerview_product.setNestedScrollingEnabled(false);
        recyclerview_product.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview_product, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productlistarraylist.get(position);
                Intent intent_product_details = new Intent(getActivity(), ProductDetailsActivity.class);
                intent_product_details.putExtra("productId",product.getProductId());
                startActivity(intent_product_details);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.invite_now:
                ((MainActivity) getActivity()).setupBottomNavigationFrom(R.id.navigation_invite);
                break;


                default:
                    break;
        }
    }

}
