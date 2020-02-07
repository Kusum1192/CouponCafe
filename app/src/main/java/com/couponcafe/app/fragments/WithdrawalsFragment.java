package com.couponcafe.app.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.couponcafe.app.R;
import com.couponcafe.app.activities.OffersDetailsActivity;
import com.couponcafe.app.activities.PaymentScreenActivity;
import com.couponcafe.app.utils.Constants;


public class WithdrawalsFragment extends Fragment implements View.OnClickListener {

    LinearLayout ll_recharge_now, ll_transfer_now;
    ImageView desc_arrow, e_wallet_arrow;
    TextView tv_user_amount, tv_user_pending,tv_transfer_now;
    Integer user_amount, user_pending,minPaylimit;
    CardView card_make_recharge,card_transfer_bank,card_gift_card,card_transfer_ewallet;
    protected  FragmentActivity mActivity;


    public WithdrawalsFragment() {

    }

    public WithdrawalsFragment(Integer userAmount, Integer pendingAmount,Integer minPaylimit) {
        this.user_amount = userAmount;
        this.user_pending = pendingAmount;
        this.minPaylimit = minPaylimit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.withdraw_fragment, container, false);



        init(view);
        return view;
    }

    private void init(View view) {
        tv_user_amount = view.findViewById(R.id.tv_user_amount);
        tv_user_pending = view.findViewById(R.id.tv_user_pending);
        tv_transfer_now = view.findViewById(R.id.tv_transfer_now);
        card_gift_card = view.findViewById(R.id.card_gift_card);
        card_make_recharge = view.findViewById(R.id.card_make_recharge);
        card_transfer_bank = view.findViewById(R.id.card_transfer_bank);
        card_transfer_ewallet = view.findViewById(R.id.card_transfer_ewallet);
        tv_user_amount.setText(Constants.getSharedPreferenceString(mActivity, "currency", "") + " " + user_amount);
        tv_user_pending.setText(Constants.getSharedPreferenceString(mActivity, "currency", "") + "" + user_pending);
        ll_recharge_now = view.findViewById(R.id.ll_recharge_now);
        ll_transfer_now = view.findViewById(R.id.ll_transfer_now);
        desc_arrow = view.findViewById(R.id.desc_arrow);
        e_wallet_arrow = view.findViewById(R.id.e_wallet_arrow);
       // desc_arrow.setOnClickListener(this);
        //e_wallet_arrow.setOnClickListener(this);
        tv_transfer_now.setOnClickListener(this);
        card_make_recharge.setOnClickListener(this);
        card_transfer_bank.setOnClickListener(this);
        card_gift_card.setOnClickListener(this);
        card_transfer_ewallet.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_transfer_ewallet:
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

            case R.id.card_make_recharge:
                Toast.makeText(mActivity, "Features coming soon.", Toast.LENGTH_SHORT).show();
//                if (ll_recharge_now.getVisibility() == View.GONE) {
//                    // it's collapsed - expand it
//                    ll_recharge_now.setVisibility(View.VISIBLE);
//                    desc_arrow.setImageResource(R.drawable.ic_arrow_drop_up);
//                } else {
//                    // it's expanded - collapse it
//                    ll_recharge_now.setVisibility(View.GONE);
//                    desc_arrow.setImageResource(R.drawable.ic_arrow_drop_down);
//                }
                break;

            case R.id.card_gift_card:
                Toast.makeText(mActivity, "Features coming soon.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_transfer_bank:
//                getDialog();
                Toast.makeText(mActivity, "Features coming soon.", Toast.LENGTH_SHORT).show();
                break;



            case R.id.tv_transfer_now:
                 if(minPaylimit <= user_amount ){
                     Intent intent_payment = new Intent(mActivity, PaymentScreenActivity.class);
                     startActivity(intent_payment);
                 }
                 else{
                     getDialog();
                 }
                break;

        }
    }



    public void getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        //Uncomment the below code to Set the message and title from the strings.xml file
        // builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("You have insufficient cashback balance for this transaction ("+minPaylimit+Constants.getSharedPreferenceString(getActivity(),"currency","")+" required atleast)")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Insufficient Cashback Balance");
        alert.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;
        }

    }
}
