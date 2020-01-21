package com.couponcafe.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.couponcafe.app.MainActivity;
import com.couponcafe.app.R;
import com.couponcafe.app.utils.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    String TAG = "testing_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.login));
        TextView tv_sign_in = findViewById(R.id.tv_sign_in);
        TextView tv_sign_up = findViewById(R.id.tv_sign_up);
        TextView tv_google_login = findViewById(R.id.tv_google_login);
        TextView tv_fb_login = findViewById(R.id.tv_fb_login);
        tv_sign_in.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        tv_google_login.setOnClickListener(this);
        tv_fb_login.setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("66833815706-r42u7iq36edg2cgcdtler9ehg66nokr3.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        getIdThread1();
    }

    public void getIdThread1() {

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String advertId = null;
                try{
                    advertId = idInfo.getId();
//                    advertisingId = advertId;
                    Constants.setSharedPreferenceString(LoginActivity.this,"adverId",advertId);
//                    Log.e("id", "getIdThread: "+advertId );
                }catch (Exception e){
                    e.printStackTrace();
                }
                return advertId;
            }
            @Override
            protected void onPostExecute(String advertId) {
//                Toast.makeText(getApplicationContext(), "advg: "+advertId, Toast.LENGTH_SHORT).show();
            }
        };
        task.execute();


    }
    @Override
    protected void onActivityResult( final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }

    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {



        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String apilevel  = String.valueOf(android.os.Build.VERSION.SDK_INT);
                            String android_id = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);

                            String devicename =  android.os.Build.MODEL;
                            String version ="";
                            int versioncode = 0;
                            try {
                                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                version = pInfo.versionName;
                                versioncode = pInfo.versionCode;
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }

                            Constants.setSharedPreferenceString(LoginActivity.this,"username",user.getDisplayName());
                            Constants.setSharedPreferenceString(LoginActivity.this,"useremail",user.getEmail());

                            Intent intent_main = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent_main);


                        } else {

                        }


                    }
                });
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_sign_in:
                Intent intent_sign_in = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent_sign_in);
                finish();

                break;

            case R.id.tv_sign_up:
                Intent intent_sign_up = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent_sign_up);
                finish();
                break;

            case R.id.tv_google_login:
                signIn();
                break;

            case R.id.tv_fb_login:

                break;
        }
    }
}
