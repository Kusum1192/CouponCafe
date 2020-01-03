package com.couponcafe.app.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.couponcafe.app.R;


public class OffersDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    TextView text_copy_code,scratch_win_dialog,mItemDescription;
    ImageView desc_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_details);

        getSupportActionBar().setTitle("Offers Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        text_copy_code = findViewById(R.id.tv_code_copy);
        mItemDescription = findViewById(R.id.mItemDescription);
        desc_arrow = findViewById(R.id.desc_arrow);
        scratch_win_dialog = findViewById(R.id.scratch_win_dialog);
        text_copy_code.setOnClickListener(this);
        desc_arrow.setOnClickListener(this);
        scratch_win_dialog.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_main_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code_copy:
                Toast.makeText(this, "text copied", Toast.LENGTH_SHORT).show();
                break;

            case R.id.scratch_win_dialog:
                //getcustomDialog();
                break;

            case R.id.desc_arrow:
                if (mItemDescription.getVisibility() == View.GONE) {
                    // it's collapsed - expand it
                    mItemDescription.setVisibility(View.VISIBLE);
                    desc_arrow.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    // it's expanded - collapse it
                    mItemDescription.setVisibility(View.GONE);
                    desc_arrow.setImageResource(R.drawable.ic_arrow_down);
                }

                break;


        }
    }

    private void getcustomDialog() {
        final Dialog dialog = new Dialog(OffersDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialoge_scratch_win);
        dialog.show();
    }
}
