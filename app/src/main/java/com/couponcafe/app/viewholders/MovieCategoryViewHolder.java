package com.couponcafe.app.viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.couponcafe.app.R;
import com.couponcafe.app.activities.BestOffersActivity;
import com.couponcafe.app.adapter.MovieCategory;

public class MovieCategoryViewHolder extends ParentViewHolder implements View.OnClickListener {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private final ImageView mArrowExpandImageView;
    private TextView mMovieTextView;
    LinearLayout ll_main_cat;
    Context context;

    public MovieCategoryViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        ll_main_cat =  itemView.findViewById(R.id.ll_main_cat);
        mMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_category);
        //msubcatMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_subcategories);

        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.iv_arrow_expand);
        ll_main_cat.setOnClickListener(this);
    }

    public void bind(MovieCategory movieCategory) {
        mMovieTextView.setText(movieCategory.getName());
        //msubcatMovieTextView.setText(movieCategory.getSubname());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_main_cat:
                Toast.makeText(context, "parent click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BestOffersActivity.class);
                context.startActivity(intent);
            break;
        }
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

            if (expanded) {
                mArrowExpandImageView.setRotation(ROTATED_POSITION);
            } else {
                mArrowExpandImageView.setRotation(INITIAL_POSITION);
            }

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                 rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mArrowExpandImageView.startAnimation(rotateAnimation);

    }
}