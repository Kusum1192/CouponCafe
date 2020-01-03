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


public class ActivitiesFragment extends Fragment implements View.OnClickListener {


   // View view = null;
    public ActivitiesFragment() {
        // Required empty public constructor
       // Toast.makeText(getActivity(), "Activities call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activities_fragment, container, false);

        Toast.makeText(getActivity(), "activity", Toast.LENGTH_SHORT).show();
      //  init(view);

        return view;
    }

    private void init(View view) {


    }


    @Override
    public void onClick(View view) {


    }
}
