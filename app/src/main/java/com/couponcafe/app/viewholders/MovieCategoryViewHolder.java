package com.couponcafe.app.viewholders;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.couponcafe.app.R;
import com.couponcafe.app.adapter.MovieCategory;

public class MovieCategoryViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private final ImageView mArrowExpandImageView;
    private TextView mMovieTextView,msubcatMovieTextView;

    public MovieCategoryViewHolder(View itemView) {
        super(itemView);
        mMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_category);
        msubcatMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_subcategories);

        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.iv_arrow_expand);
    }

    public void bind(MovieCategory movieCategory) {
        mMovieTextView.setText(movieCategory.getName());
        msubcatMovieTextView.setText(movieCategory.getSubname());
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