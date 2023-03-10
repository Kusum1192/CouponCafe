package com.couponhub.app.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.couponhub.app.R;
import com.couponhub.app.activities.OffersDetailsActivity;
import com.couponhub.app.models.SliderDatum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Sliding_Adapter_For_viewpager_main extends PagerAdapter {

    private ArrayList<SliderDatum> images;
    private LayoutInflater inflater;
    private Context context;

    public Sliding_Adapter_For_viewpager_main(Context context, ArrayList<SliderDatum> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.image_viewpager_mainactivity_layout, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
       // myImage.setImageResource(images.get(position).getImageUrl());

        Picasso.get().load(images.get(position).getImageUrl())
                .placeholder(R.drawable.ic_placeholder_small)
                .error(R.drawable.ic_placeholder_small)
                .into(myImage);

        view.addView(myImageLayout, 0);

        //listening to image click
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OffersDetailsActivity.class);
                intent.putExtra("offerId",images.get(position).getOfferId());
                context.startActivity(intent);
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });


        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}