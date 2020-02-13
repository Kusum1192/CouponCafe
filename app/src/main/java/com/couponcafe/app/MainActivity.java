package com.couponcafe.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.couponcafe.app.activities.NotificationActivity;
import com.couponcafe.app.activities.UserTransactionsActivity;
import com.couponcafe.app.fragments.CategoriesFragment;
import com.couponcafe.app.fragments.HelpFragment;
import com.couponcafe.app.fragments.HomeFragment;
import com.couponcafe.app.fragments.InviteAndEarnFragment;
import com.couponcafe.app.fragments.ProfileFragment;
import com.couponcafe.app.fragments.ProfileTestingFragment;
import com.couponcafe.app.interfaces.refreshLayout;
import com.couponcafe.app.utils.BottomNavigationViewHelper;
import com.couponcafe.app.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout drawer;
    String TAG = "testing";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBottomNavigation();

        if (savedInstanceState == null) {
            // loadHomeFragment();
            HomeFragment homeFragment = new HomeFragment();
            HoldAllFragments(homeFragment);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);

        //create default navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView tv_username = headerView.findViewById(R.id.tv_username);
        TextView tv_useremail = headerView.findViewById(R.id.tv_useremail);
        ImageView imageView = headerView.findViewById(R.id.imageView);


        tv_username.setText(Constants.getSharedPreferenceString(MainActivity.this, "username", ""));
        tv_useremail.setText(Constants.getSharedPreferenceString(MainActivity.this, "useremail", ""));

        Picasso.get().load(Constants.getSharedPreferenceString(MainActivity.this, "userimage", ""))
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((imageView));


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the bottom_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.title_main_menu, menu);

//        TextView userCoins = (TextView) menu.findItem(R.id.action_wallet).getActionView().findViewById(R.id.toolbar_total_coin);
//        userCoins.setText(Constants.getSharedPreferenceString(MainActivity.this, "currency", "") + " " + Constants.getSharedPreferenceInt(MainActivity.this, "userAmount", 0));
        invalidateOptionsMenu();

        MenuItem action_search = menu.findItem(R.id.action_search);

       MenuItem action_wallet = menu.findItem(R.id.action_wallet);
       action_wallet.setTitle(Constants.getSharedPreferenceString(MainActivity.this, "currency", "") + " " + Constants.getSharedPreferenceInt(MainActivity.this, "userAmount", 0));
       action_wallet.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem menuItem) {
               setupBottomNavigationFrom(R.id.navigation_profile);
               return false;
           }
       });

        MenuItem NotificationIcon = menu.findItem(R.id.action_notification);

        NotificationIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
                return false;
            }
        });





        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "clickm y", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String BASE_URL_WEB = "https://couponhub.app/info-files/";

        switch (id) {
            case R.id.nav_home:
                setupBottomNavigationFrom(R.id.navigation_home);
                break;
            case R.id.nav_cashactivity:
                setupBottomNavigationFrom(R.id.navigation_profile);
                break;

            case R.id.nav_withdraw:
                setupBottomNavigationFrom(R.id.navigation_profile);
                break;

            case R.id.nav_invite:
                setupBottomNavigationFrom(R.id.navigation_invite);
                break;

            case R.id.nav_user_transaction:
                 Intent intent_user_transaction = new Intent(MainActivity.this, UserTransactionsActivity.class);
                 startActivity(intent_user_transaction);
                break;
            case R.id.nav_special_offer:
                Toast.makeText(this, "Coming Soon..!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_aboutus:
                webViewLoad(BASE_URL_WEB + "about-us.html", "About Us");
                break;


            case R.id.nav_privacy:
                webViewLoad(BASE_URL_WEB + "privacy-policy.html", "Privacy Policy");
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void webViewLoad(String url, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);

        WebView wv = new WebView(MainActivity.this);
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

    public void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        //loadHomeFragment();
                        HomeFragment homeFragment = new HomeFragment();
                        HoldAllFragments(homeFragment);
                        return true;
                    }

                    case R.id.navigation_categories: {
//                        loadCategoriesFragment();
                        CategoriesFragment categoriesFragment = new CategoriesFragment();
                        HoldAllFragments(categoriesFragment);
                        return true;
                    }

                    case R.id.navigation_invite: {
//                        loadInviteAndEarnFragment();
                        InviteAndEarnFragment inviteAndEarnFragment = new InviteAndEarnFragment();
                        HoldAllFragments(inviteAndEarnFragment);
                        return true;
                    }

                    case R.id.navigation_help: {
                        //loadHelpFragment();
                        HelpFragment helpFragment = new HelpFragment();
                        HoldAllFragments(helpFragment);
                        return true;

                    }
                    case R.id.navigation_profile: {
//                        loadProfileFragment();
                        ProfileFragment profileFragment = new ProfileFragment();
                        HoldAllFragments(profileFragment);
//                        ProfileTestingFragment profileTestingFragment = new ProfileTestingFragment();
//                        HoldAllFragments(profileTestingFragment);

                        return true;
                    }


                }
                return false;
            }
        });
    }

    public void HoldAllFragments(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }


    public void setupBottomNavigationFrom(final int id) {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.getMenu().findItem(id).setChecked(true);
        mBottomNavigationView.getMenu().performIdentifierAction(id, 0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



}
