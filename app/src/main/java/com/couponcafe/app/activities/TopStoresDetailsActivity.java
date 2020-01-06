package com.couponcafe.app.activities;

import android.app.Dialog;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.TopStores;


public class TopStoresDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardview_offer_Detials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topstore_details);


        getSupportActionBar().setTitle("Tops Store Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cardview_offer_Detials = findViewById(R.id.cardview_offer_Detials);
        cardview_offer_Detials.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

            case R.id.cardview_offer_Detials:
                Intent intent_ofer_details = new Intent(TopStoresDetailsActivity.this,OffersDetailsActivity.class);
                startActivity(intent_ofer_details);

                break;
        }
    }


}
