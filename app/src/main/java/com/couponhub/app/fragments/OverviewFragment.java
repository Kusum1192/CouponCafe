package com.couponhub.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.couponhub.app.MainActivity;
import com.couponhub.app.R;
import com.couponhub.app.activities.ProductDetailsActivity;
import com.couponhub.app.adapter.ProductListAdapter;
import com.couponhub.app.adapter.RecyclerTouchListener;
import com.couponhub.app.models.Product;
import com.couponhub.app.utils.Constants;

import java.util.ArrayList;


public class OverviewFragment extends Fragment implements View.OnClickListener {


    TextView textView_invite, tv_user_amount, tv_user_pending;
    Integer user_amount,user_pending;
    RecyclerView recyclerview_product;
    ProductListAdapter productListAdapter;
    ArrayList<Product> productlistarraylist;
    protected FragmentActivity mActivity;
    String TAG = "overview_testing";

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
        tv_user_amount.setText(Constants.getSharedPreferenceString(mActivity,"currency","")+" "+user_amount);
        tv_user_pending.setText(Constants.getSharedPreferenceString(mActivity,"currency","")+""+user_pending);
        textView_invite.setOnClickListener(this);

        try{
        productListAdapter = new ProductListAdapter(productlistarraylist,mActivity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerview_product.setLayoutManager(mLayoutManager);
        recyclerview_product.setAdapter(productListAdapter);
        //ViewCompat.setNestedScrollingEnabled(recyclerview_product, false);
        recyclerview_product.setNestedScrollingEnabled(false);
        recyclerview_product.addOnItemTouchListener(new RecyclerTouchListener(mActivity, recyclerview_product, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productlistarraylist.get(position);
                Intent intent_product_details = new Intent(mActivity, ProductDetailsActivity.class);
                intent_product_details.putExtra("productId",product.getProductId());
                startActivity(intent_product_details);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "init: "+e );
        }

    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.invite_now:
                ((MainActivity) mActivity).setupBottomNavigationFrom(R.id.navigation_invite);
                break;


                default:
                    break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;
        }

    }

}
