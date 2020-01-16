package com.couponcafe.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.couponcafe.app.MainActivity;
import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;
import com.couponcafe.app.utils.Constants;


public class OverviewFragment extends Fragment implements View.OnClickListener {


    TextView textView_invite, tv_user_amount, tv_user_pending;
    Integer user_amount,user_pending;

    public OverviewFragment() {
        // Required empty public constructor

    }

    public OverviewFragment(Integer userAmount, Integer pendingAmount) {
        this.user_amount = userAmount;
        this.user_pending = pendingAmount;


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
        textView_invite = view.findViewById(R.id.invite_now);
        tv_user_amount = view.findViewById(R.id.tv_user_amount);
        tv_user_pending = view.findViewById(R.id.tv_user_pending);
        tv_user_amount.setText(Constants.getSharedPreferenceString(getActivity(),"currency","")+" "+user_amount);
        tv_user_pending.setText(Constants.getSharedPreferenceString(getActivity(),"currency","")+""+user_pending);
        textView_invite.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.invite_now:
                ((MainActivity) getActivity()).setupBottomNavigationFrom(R.id.navigation_invite);
                break;
        }
    }

}
