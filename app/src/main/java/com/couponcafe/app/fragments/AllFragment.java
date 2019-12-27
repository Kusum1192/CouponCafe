package com.couponcafe.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;


public class AllFragment extends Fragment implements View.OnClickListener {

    CardView offer_one;
    View view = null;
    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.all_fragment, container, false);

        init(view);

        return view;
    }

    private void init(View view) {

        offer_one = view.findViewById(R.id.offer_one);
        offer_one.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.offer_one:
                Intent intent_offerdetails = new Intent(getActivity(), OffersDetailsActivity.class);
                startActivity(intent_offerdetails);
                break;
        }
    }
}
