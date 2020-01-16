package com.couponcafe.app.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.couponcafe.app.BuildConfig;
import com.couponcafe.app.R;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.InviteFriendModel;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteAndEarn extends Fragment implements View.OnClickListener {

    ImageView copyReferCode,invite_share_people_img;
    LinearLayout viewDetails,ll_invite_other,ll_whatsapp,ll_telegram;
    Button shareNow;
    TextView Refer_code,invite_text;
    String refferal_code,invitefburl,inviteTextUrl;

    ProgressDialog progressDialog;

    public InviteAndEarn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_and_earn, container, false);

        copyReferCode = view.findViewById(R.id.tv_code_copy);
        invite_share_people_img = view.findViewById(R.id.invite_share_people_img);
        invite_text = view.findViewById(R.id.invite_text);
        shareNow = view.findViewById(R.id.share_now_button);
        viewDetails = view.findViewById(R.id.view_Details);
        ll_invite_other = view.findViewById(R.id.ll_invite_other);
        ll_whatsapp = view.findViewById(R.id.ll_whatsapp);
        ll_telegram = view.findViewById(R.id.ll_telegram);
        Refer_code = view.findViewById(R.id.refer_code);

        copyReferCode.setOnClickListener(this);
        shareNow.setOnClickListener(this);
        viewDetails.setOnClickListener(this);
        ll_invite_other.setOnClickListener(this);
        ll_telegram.setOnClickListener(this);
        ll_whatsapp.setOnClickListener(this);

        getinviteData();

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

            case R.id.ll_invite_other:
//                shareApp();
                shareAppOther();
                break;

            case R.id.ll_whatsapp:
                shareOnWhatsapp();
                break;

            case R.id.ll_telegram:
                shareOnTelegram();
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

    public void shareAppOther(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CouponCafe");
            String shareMessage= "\nLet me recommend you this application\n\n";
            //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void getinviteData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InviteFriendModel> call = apiService.getinviteData(Constants.getSharedPreferenceInt(getActivity(),"userId",0),
                Constants.getSharedPreferenceString(getActivity(),"securitytoken",""),
                Constants.getSharedPreferenceString(getActivity(),"versionName",""),
                Constants.getSharedPreferenceInt(getActivity(),"versionCode",0));

        if(!((Activity) getActivity()).isFinishing()) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<InviteFriendModel>() {
            @Override
            public void onResponse(Call<InviteFriendModel>call, Response<InviteFriendModel> response) {
                dismissProgressDialog();
                if(response!=null){
                    if(response.isSuccessful()){
                        if(response.body().getStatus()==200){
                            invitefburl = response.body().getInviteFbUrl();
                            inviteTextUrl = response.body().getInviteTextUrl();

                            Picasso.get().load(response.body().getInviteImgurl())
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .into((invite_share_people_img));
                            invite_text.setText(response.body().getInviteText());

                        }else{
                            Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else{
                    Toast.makeText(getActivity(),getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<InviteFriendModel>call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });


    }

    public void shareApp(){
        try {

            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo resolveInfo : resInfo) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                    targetedShareIntent.setType("text/plain");
                    targetedShareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject to be shared");
                    if (TextUtils.equals(packageName, "com.facebook.katana")) {
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, invitefburl);
                    } else {
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
                    }
                    targetedShareIntent.setPackage(packageName);
                    targetedShareIntents.add(targetedShareIntent);
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                startActivity(chooserIntent);
            }




        } catch(Exception e) {
            e.toString();
        }
    }

    public void shareOnWhatsapp(){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
        try {
            getActivity().startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnTelegram() {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(getActivity().getApplicationContext(), appName);
        if (isAppInstalled)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);//
            getActivity().startActivity(Intent.createChooser(myIntent, "Share with"));
        }
        else
        {
            Toast.makeText(getActivity(), "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }


    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        dismissProgressDialog();
        super.onPause();
    }
}
