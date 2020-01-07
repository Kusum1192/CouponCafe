package com.couponcafe.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.couponcafe.app.R;

public class ViewAllTopOffersActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardview_top_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_top_offers);


        getSupportActionBar().setTitle("Top Stores");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        cardview_top_store  = findViewById(R.id.cardview_top_store);
        cardview_top_store.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_top_store:
                Intent intent_topstore_detials = new Intent(ViewAllTopOffersActivity.this,TopStoresDetailsActivity.class);
                startActivity(intent_topstore_detials);
                break;
        }
    }
}
