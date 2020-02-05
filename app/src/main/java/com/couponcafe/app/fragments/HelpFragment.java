package com.couponcafe.app.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.BuildConfig;
import com.couponcafe.app.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.couponcafe.app.utils.ApiClient.BASE_URL;


public class HelpFragment extends Fragment implements View.OnClickListener {
    LinearLayout AboutUs,ContactUs,PrivacyPolicy,TermsofService,HowsitWork,Faq,product_catelog,return_policy;
    TextView version;
    View view;
    String BASE_URL_WEB="https://couponhub.app/info-files/";
    protected  FragmentActivity mActivity;

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
        product_catelog = view.findViewById(R.id.product_catelog);
        return_policy = view.findViewById(R.id.return_cancel);

        String versionName = BuildConfig.VERSION_NAME;
        version = view.findViewById(R.id.versionCode);
        version.setText("Version : "+versionName);

        AboutUs.setOnClickListener(this);
        ContactUs.setOnClickListener(this);
        PrivacyPolicy.setOnClickListener(this);
        TermsofService.setOnClickListener(this);
        HowsitWork.setOnClickListener(this);
        Faq.setOnClickListener(this);
        return_policy.setOnClickListener(this);
        product_catelog.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.aboutUs:
                webViewLoad(BASE_URL_WEB+"about-us.html","About Us");
                break;
            case R.id.contactUs:
                webViewLoad(BASE_URL_WEB+"contact-us.html","Contact Us");
                break;
            case R.id.privacyPolicy:
                webViewLoad(BASE_URL_WEB+"privacy-policy.html","Privacy Policy");
                break;
            case R.id.termsOfServices:
                webViewLoad(BASE_URL_WEB+"terms-conditions.html","Terms Of Services");
                break;
            case R.id.howsItWork:
                webViewLoad(BASE_URL_WEB+"how-it-works.html","How It Works");
                break;
            case R.id.faq:
                webViewLoad(BASE_URL_WEB+"faq.html","FAQ");
                break;

            case R.id.product_catelog:
                webViewLoad(BASE_URL_WEB+"app-services.html","Product / Service Catelog");
                break;

            case R.id.return_cancel:
                webViewLoad(BASE_URL_WEB+"refund-cancellation.html","Refund, & Cancellation policy");
                break;

        }
    }

    private void webViewLoad(String url,String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title);

        WebView wv = new WebView(mActivity);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        builder.setView(wv);
        builder.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;
        }

    }

}
