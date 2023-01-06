package com.couponhub.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.couponhub.app.MainActivity;
import com.couponhub.app.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.signup));
        TextView tv_sign_up = findViewById(R.id.tv_sign_up);
        TextView tv_existing_user = findViewById(R.id.tv_existing_user);
        tv_sign_up.setOnClickListener(this);
        tv_existing_user.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.tv_sign_up:
                Intent intent_main = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent_main);
                finish();

                break;

            case R.id.tv_existing_user:
                Intent intentexit_user = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intentexit_user);
                finish();
                break;
        }

    }
}
