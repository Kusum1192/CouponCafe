package com.couponcafe.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;


public class WithdrawalsFragment extends Fragment implements View.OnClickListener {

    LinearLayout ll_recharge_now,ll_transfer_now;
    ImageView desc_arrow,e_wallet_arrow;
   // View view = null;
    public WithdrawalsFragment() {
        // Required empty public constructor
       // Toast.makeText(getActivity(), "withdraw call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View  view = inflater.inflate(R.layout.withdraw_fragment, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
     ll_recharge_now = view.findViewById(R.id.ll_recharge_now);
     ll_transfer_now = view.findViewById(R.id.ll_transfer_now);
     desc_arrow = view.findViewById(R.id.desc_arrow);
     e_wallet_arrow = view.findViewById(R.id.e_wallet_arrow);
     desc_arrow.setOnClickListener(this);
     e_wallet_arrow.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
     switch (view.getId()){
      case R.id.desc_arrow:
       if (ll_recharge_now.getVisibility() == View.GONE) {
        // it's collapsed - expand it
        ll_recharge_now.setVisibility(View.VISIBLE);
        desc_arrow.setImageResource(R.drawable.ic_arrow_drop_up);
       } else {
        // it's expanded - collapse it
        ll_recharge_now.setVisibility(View.GONE);
        desc_arrow.setImageResource(R.drawable.ic_arrow_drop_down);
       }
       break;

      case R.id.e_wallet_arrow:
       if (ll_transfer_now.getVisibility() == View.GONE) {
        // it's collapsed - expand it
        ll_transfer_now.setVisibility(View.VISIBLE);
        e_wallet_arrow.setImageResource(R.drawable.ic_arrow_drop_up);
       } else {
        // it's expanded - collapse it
        ll_transfer_now.setVisibility(View.GONE);
        e_wallet_arrow.setImageResource(R.drawable.ic_arrow_drop_down);
       }
       break;

     }
    }
}
