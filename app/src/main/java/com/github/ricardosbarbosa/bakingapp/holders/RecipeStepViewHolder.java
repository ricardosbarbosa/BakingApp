package com.github.ricardosbarbosa.bakingapp.holders;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.model.RecipeStep;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    @BindView(R.id.short_description) public TextView mShortDescriptionView;
    @BindView(R.id.thumbnail) public ImageView mThumbnailView;

    public RecipeStep mItem;

    public RecipeStepViewHolder(View view) {
        super(view);
        mView = view;
        ButterKnife.bind(this, view);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mShortDescriptionView.getText() + "'";
    }

    public void setup(RecipeStep recipeStep) {
        mItem = recipeStep;
        mShortDescriptionView.setText(recipeStep.shortDescription);
        if (recipeStep.thumbnailURL != null && !recipeStep.thumbnailURL.isEmpty())
            Picasso.with(mView.getContext()).load(recipeStep.thumbnailURL).into(mThumbnailView);
    }
}