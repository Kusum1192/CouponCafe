package com.couponcafe.app.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.R;
import com.couponcafe.app.fragments.AllFragment;
import com.couponcafe.app.fragments.FoodDiningFragment;
import com.couponcafe.app.fragments.TravelFragment;
import com.couponcafe.app.utils.Sliding_Adapter_For_viewpager_main;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private CircleIndicator indicator;
    int images[] = {R.drawable.time_prime, R.drawable.oyoroom, R.drawable.time_prime, R.drawable.oyoroom};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_activity);

        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        init();
//        Intent intent = getIntent();
//        if(intent!=null){
//            String catId = String.valueOf(intent.getIntExtra("subcatId",0));
//            String subcatName = intent.getStringExtra("subcatName");
//
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        mPager = (ViewPager)findViewById(R.id.pager);
        indicator = (CircleIndicator)findViewById(R.id.indicator);

        mPager.setAdapter(new Sliding_Adapter_For_viewpager_main(ProductDetailsActivity.this, images));
        indicator.setViewPager(mPager);
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }




    @Override
    public void onClick(View view) {

    }


    public class Sliding_Adapter_For_viewpager_main extends PagerAdapter {

        LayoutInflater inflater;
        Context context;
        int images[];

        public Sliding_Adapter_For_viewpager_main(Context context,int images[]) {
            this.context = context;
            this.images = images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View myImageLayout = inflater.inflate(R.layout.image_viewpager_mainactivity_layout, view, false);
            ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
             myImage.setImageResource(images[position]);

//            Picasso.get().load(images.get(position).getImageUrl())
//                    .placeholder(R.drawable.placeholder)
//                    .error(R.drawable.placeholder)
//                    .into(myImage);

            view.addView(myImageLayout, 0);


            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
