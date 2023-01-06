package com.couponhub.app.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.couponhub.app.BuildConfig;
import com.couponhub.app.R;
import com.couponhub.app.adapter.InviteUserAdapter;
import com.couponhub.app.interfaces.APIService;
import com.couponhub.app.models.InviteFriendModel;
import com.couponhub.app.models.InvitedUser;
import com.couponhub.app.utils.ApiClient;
import com.couponhub.app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteAndEarnFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    ImageView copyReferCode, invite_share_people_img;
    LinearLayout viewDetails, ll_invite_other, ll_whatsapp, ll_telegram;
    Button shareNow;
    TextView Refer_code, invite_text;
    String refferal_code, invitefburl, inviteTextUrl;

    ProgressDialog progressDialog;
    RecyclerView recyclerview;
    protected FragmentActivity mActivity;
    SwipeRefreshLayout refreshLayout;
    String TAG = "testing_invite";

    public InviteAndEarnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_and_earn, container, false);

        try {

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        copyReferCode = view.findViewById(R.id.tv_code_copy);
        recyclerview = view.findViewById(R.id.recyclerview);
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
        refreshLayout.setOnRefreshListener(this);

        getinviteData();

        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "onCreateView: "+e );
        }

        return view;
    }

    @Override
    public void onRefresh() {
        getinviteData();
        refreshLayout.setRefreshing(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_code_copy:
                if (Refer_code.getText().toString().equals("")) {
                    Toast.makeText(mActivity, "you have no Referral Code", Toast.LENGTH_SHORT).show();
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

    private void CopyReferCodeToClipboard(String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText(mActivity, "Referral code copied Successfully"+text, Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mActivity, "Referral code copied Successfully"+text, Toast.LENGTH_SHORT).show();
        }
    }

    public void shareAppOther() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CouponCafe");
            String shareMessage = "\nLet me recommend you this application\n\n";
            //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void getinviteData() {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<InviteFriendModel> call = apiService.getinviteData(Constants.getSharedPreferenceInt(mActivity, "userId", 0),
                Constants.getSharedPreferenceString(mActivity, "securitytoken", ""),
                BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE);

        if (!((Activity) mActivity).isFinishing()) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<InviteFriendModel>() {
            @Override
            public void onResponse(Call<InviteFriendModel> call, Response<InviteFriendModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            invitefburl = response.body().getInviteFbUrl();
                            inviteTextUrl = response.body().getInviteTextUrl();

                            Picasso.get().load(response.body().getInviteImgurl())
                                    .placeholder(R.drawable.ic_placeholder_small)
                                    .error(R.drawable.ic_placeholder_small)
                                    .into((invite_share_people_img));

                            invite_text.setText(response.body().getInviteText());
                            ArrayList<InvitedUser> invitedUserArrayList = response.body().getInvitedUsers();

                            InviteUserAdapter inviteUserAdapter = new InviteUserAdapter(invitedUserArrayList, mActivity);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
                            recyclerview.setLayoutManager(mLayoutManager);
                            recyclerview.setAdapter(inviteUserAdapter);
                            recyclerview.setNestedScrollingEnabled(false);

                        } else {
                            Toast.makeText(mActivity, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(mActivity, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<InviteFriendModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });


    }

    public void shareApp() {
        try {

            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            List<ResolveInfo> resInfo = mActivity.getPackageManager().queryIntentActivities(shareIntent, 0);
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


        } catch (Exception e) {
            e.toString();
        }
    }

    public void shareOnWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);
        try {
            mActivity.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mActivity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnTelegram() {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(mActivity.getApplicationContext(), appName);
        if (isAppInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, inviteTextUrl);//
            mActivity.startActivity(Intent.createChooser(myIntent, "Share with"));
        } else {
            Toast.makeText(mActivity, "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        }

    }
}

