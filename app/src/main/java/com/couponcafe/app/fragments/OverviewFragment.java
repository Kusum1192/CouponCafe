package com.couponcafe.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;


public class OverviewFragment extends Fragment implements View.OnClickListener {

   // CardView cardview_account_balance;
   // View view = null;
    public OverviewFragment() {
        // Required empty public constructor
        //Toast.makeText(getActivity(), "overview call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View  view = inflater.inflate(R.layout.overview_fragment, container, false);

     //Toast.makeText(getActivity(), "overview", Toast.LENGTH_SHORT).show();
        //init(view);

        return view;
    }

    private void init(View view) {

//        cardview_account_balance = view.findViewById(R.id.cardview_account_balance);
//        cardview_account_balance.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

//        switch (view.getId()){
//            case R.id.cardview_account_balance:
//               // Intent intent_offerdetails = new Intent(getActivity(), OffersDetailsActivity.class);
//              //  startActivity(intent_offerdetails);
//                break;
//        }
    }
}
