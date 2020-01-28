package com.couponcafe.app;

import android.content.Intent;
import android.os.Bundle;

import com.couponcafe.app.activities.NotificationActivity;
import com.couponcafe.app.fragments.BlankFragment;
import com.couponcafe.app.fragments.CategoriesFragment;
import com.couponcafe.app.fragments.HelpFragment;
import com.couponcafe.app.fragments.HomeFragment;
import com.couponcafe.app.fragments.InviteAndEarn;
import com.couponcafe.app.fragments.ProfileFragment;
import com.couponcafe.app.utils.BottomNavigationViewHelper;
import com.couponcafe.app.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBottomNavigation();

        if (savedInstanceState == null) {
            loadHomeFragment();
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);


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


        tv_username.setText(Constants.getSharedPreferenceString(MainActivity.this,"username",""));
        tv_useremail.setText(Constants.getSharedPreferenceString(MainActivity.this,"useremail",""));

        Picasso.get().load(Constants.getSharedPreferenceString(MainActivity.this,"userimage",""))
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into((imageView));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the bottom_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.title_main_menu, menu);

        TextView userCoins = (TextView) menu.findItem(R.id.action_wallet).getActionView().findViewById(R.id.toolbar_total_coin);
        userCoins.setText(Constants.getSharedPreferenceString(MainActivity.this,"currency","")+" "+Constants.getSharedPreferenceString(MainActivity.this,"userAmount",""));
        invalidateOptionsMenu();
        MenuItem NotificationIcon = menu.findItem(R.id.action_notification);
        NotificationIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:

                // Do Activity menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        loadHomeFragment();
                        return true;
                    }

                    case R.id.navigation_categories: {
                        loadCategoriesFragment();
                        return true;
                    }

                    case R.id.navigation_invite: {
                        loadShopingAssistanceFragment();
                        return true;
                    }

                    case R.id.navigation_help: {
                    loadHelpFragment();
                        return true;

                    }
                    case R.id.navigation_profile: {
                        loadProfileFragment();
                        return true;
                    }
                }
                return false;
            }
        });
    }


    public void loadHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

    public void loadCategoriesFragment() {
        CategoriesFragment fragment = new CategoriesFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

    public void loadShopingAssistanceFragment() {
        InviteAndEarn fragment = new InviteAndEarn();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }
    public void loadHelpFragment() {
        HelpFragment fragment = new HelpFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

    public void loadProfileFragment() {
        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }



    public  void setupBottomNavigationFrom(final int id) {
        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
