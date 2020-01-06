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

import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;


public class OverviewFragment extends Fragment implements View.OnClickListener {

   // CardView cardview_account_balance;
   // View view = null;

 TextView textView_invite ;
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
        init(view);

        return view;
    }

    private void init(View view) {

     textView_invite = view.findViewById(R.id.invite_now);
     textView_invite.setOnClickListener(this);

//        cardview_account_balance = view.findViewById(R.id.cardview_account_balance);
//        cardview_account_balance.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.invite_now:
                clearBackStack();
                open_profile_edit_Fragment(new InviteAndEarn());
                break;
        }
    }

 private void open_profile_edit_Fragment(Fragment fragment) {
  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
  fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
  fragmentTransaction.add(R.id.nav_host_fragment, fragment, fragment.getClass().getName());
  fragmentTransaction.addToBackStack(null);
  fragmentTransaction.commit();
 }

 public void clearBackStack() {

  int backStackEntryCount = getActivity().getSupportFragmentManager().getBackStackEntryCount();
  if (backStackEntryCount > 0) {
   for (int i = 0; i < backStackEntryCount; i++) {
    FragmentManager.BackStackEntry first = getActivity().getSupportFragmentManager()
            .getBackStackEntryAt(i);
            getActivity().getSupportFragmentManager().popBackStack(first.getId(),
            FragmentManager.POP_BACK_STACK_INCLUSIVE);
   }
  }
 }
}
