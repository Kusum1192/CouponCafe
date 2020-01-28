package com.couponcafe.app.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.couponcafe.app.R;
import com.couponcafe.app.fragments.AllFragment;
import com.couponcafe.app.fragments.FoodDiningFragment;
import com.couponcafe.app.fragments.TravelFragment;
import com.couponcafe.app.interfaces.APIService;
import com.couponcafe.app.models.ProductDetails;
import com.couponcafe.app.models.ProductDetailsModel;
import com.couponcafe.app.models.SliderDatum;
import com.couponcafe.app.utils.ApiClient;
import com.couponcafe.app.utils.CircleTransform;
import com.couponcafe.app.utils.Constants;
import com.couponcafe.app.utils.Sliding_Adapter_For_viewpager_main;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private CircleIndicator indicator;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_activity);

        getSupportActionBar().setTitle("Product Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent = getIntent();
        if (intent != null) {
            String productId = String.valueOf(intent.getIntExtra("productId", 0));
            getProductDetails(productId);
        }
    }


    private void getProductDetails(String productId) {

        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ProductDetailsModel> call = apiService.getProductData(Constants.getSharedPreferenceInt(ProductDetailsActivity.this, "userId", 0),
                Constants.getSharedPreferenceString(ProductDetailsActivity.this, "securitytoken", ""),
                Constants.getSharedPreferenceString(ProductDetailsActivity.this, "versionName", ""),
                Constants.getSharedPreferenceInt(ProductDetailsActivity.this, "versionCode", 0),
                productId);

        if (!((Activity) ProductDetailsActivity.this).isFinishing()) {
            progressDialog = new ProgressDialog(ProductDetailsActivity.this);
            progressDialog.setMessage(getString(R.string.loadingwait));
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        call.enqueue(new Callback<ProductDetailsModel>() {
            @Override
            public void onResponse(Call<ProductDetailsModel> call, final Response<ProductDetailsModel> response) {
                dismissProgressDialog();
                if (response != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {

                            ProductDetails productDetails = response.body().getProductDetails();
                            ArrayList<SliderDatum> sliderDatumArrayList = productDetails.getSliderData();

                            mPager = (ViewPager) findViewById(R.id.pager);
                            indicator = (CircleIndicator) findViewById(R.id.indicator);

                            mPager.setAdapter(new Sliding_Adapter_For_viewpager_main(ProductDetailsActivity.this, sliderDatumArrayList));
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

                            TextView tv_product_title = findViewById(R.id.tv_product_title);
                            TextView tv_product_price = findViewById(R.id.tv_product_price);
                            TextView tv_discount_price = findViewById(R.id.tv_discount_price);
                            TextView ratingUsers = findViewById(R.id.tv_rating_user);
                            TextView tv_description = findViewById(R.id.tv_description);
                            TextView tv_product_specificaion = findViewById(R.id.tv_product_specificaion);
                            AppCompatRatingBar  appCompatRatingBar = findViewById(R.id.rating);
                            tv_product_title.setText(productDetails.getProductName());
                            tv_product_price.setText(productDetails.getProductPrice());
                            tv_discount_price.setText(productDetails.getDiscountedPrice());
                            ratingUsers.setText(productDetails.getRatingUsers());
                            tv_description.setText(productDetails.getShortDescription());
                            tv_product_specificaion.setText(productDetails.getSpecifications());
                            appCompatRatingBar.setRating(Float.parseFloat(""+productDetails.getRating()));

//                            tv_total.setText(Constants.getSharedPreferenceString(getActivity(),"currency","")+""+response.body().getUserSavings());


                        } else {
                            Toast.makeText(ProductDetailsActivity.this, getString(R.string.systemmessage) + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(ProductDetailsActivity.this, getString(R.string.systemmessage) + response.errorBody(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("response", t.toString());
            }
        });


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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View view) {

    }


    public class Sliding_Adapter_For_viewpager_main extends PagerAdapter {

        LayoutInflater inflater;
        Context context;
        ArrayList<SliderDatum> sliderData;

        public Sliding_Adapter_For_viewpager_main(Context context, ArrayList<SliderDatum> sliderData) {
            this.context = context;
            this.sliderData = sliderData;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return sliderData.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View myImageLayout = inflater.inflate(R.layout.image_viewpager_mainactivity_layout, view, false);
            ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
//             myImage.setImageResource(images[position]);

            Picasso.get().load(sliderData.get(position).getImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(myImage);

            view.addView(myImageLayout, 0);


            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
