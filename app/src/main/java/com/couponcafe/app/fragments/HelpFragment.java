package com.couponcafe.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.BuildConfig;
import com.couponcafe.app.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class HelpFragment extends Fragment implements View.OnClickListener {
    LinearLayout AboutUs,ContactUs,PrivacyPolicy,TermsofService,HowsitWork,Faq;
    TextView version;
    View view;

    public HelpFragment() {
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
        view = inflater.inflate(R.layout.help_fragment, container, false);
        AboutUs = view.findViewById(R.id.aboutUs);
        ContactUs = view.findViewById(R.id.contactUs);
        PrivacyPolicy = view.findViewById(R.id.privacyPolicy);
        TermsofService = view.findViewById(R.id.termsOfServices);
        HowsitWork = view.findViewById(R.id.howsItWork);
        Faq = view.findViewById(R.id.faq);

        final String versionName = BuildConfig.VERSION_NAME;
        version = view.findViewById(R.id.versionCode);
        version.setText("Version : "+versionName);

        AboutUs.setOnClickListener(this);
        ContactUs.setOnClickListener(this);
        PrivacyPolicy.setOnClickListener(this);
        TermsofService.setOnClickListener(this);
        HowsitWork.setOnClickListener(this);
        Faq.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.aboutUs:
                Toast.makeText(getActivity(), "About us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contactUs:
                Toast.makeText(getActivity(), "contactUs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.privacyPolicy:
                Toast.makeText(getActivity(), "privacyPolicy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.termsOfServices:
                Toast.makeText(getActivity(), "termsOfServices", Toast.LENGTH_SHORT).show();
                break;
            case R.id.howsItWork:
                Toast.makeText(getActivity(), "howsItWork", Toast.LENGTH_SHORT).show();
                break;
            case R.id.faq:
                Toast.makeText(getActivity(), "faq", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
