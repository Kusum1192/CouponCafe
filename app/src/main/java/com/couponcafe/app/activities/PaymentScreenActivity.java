package com.couponcafe.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.couponcafe.app.R;

public class PaymentScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);

        getSupportActionBar().setTitle(getString(R.string.withdrawal));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
