package com.couponcafe.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.couponcafe.app.R;

public class InviteAndEarn extends Fragment implements View.OnClickListener {

    ImageView copyReferCode;
    LinearLayout viewDetails;
    Button shareNow;
    TextView Refer_code;
    String refferal_code;


    public InviteAndEarn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_and_earn, container, false);

        copyReferCode = view.findViewById(R.id.tv_code_copy);
        shareNow = view.findViewById(R.id.share_now_button);
        viewDetails = view.findViewById(R.id.view_Details);
        Refer_code = view.findViewById(R.id.refer_code);

        copyReferCode.setOnClickListener(this);
        shareNow.setOnClickListener(this);
        viewDetails.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_code_copy:
                if (Refer_code.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "you have no Referral Code", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Referral code is", Refer_code.getText().toString() + "");
                    refferal_code = Refer_code.getText().toString();
                    CopyReferCodeToClipboard(refferal_code);
                }
                break;


            case R.id.share_now_button:
                break;
            case R.id.view_Details:
                break;


        }

    }

    private void CopyReferCodeToClipboard (String text){
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText(getActivity(), "Referral code copied Successfully", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Referral code copied Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
