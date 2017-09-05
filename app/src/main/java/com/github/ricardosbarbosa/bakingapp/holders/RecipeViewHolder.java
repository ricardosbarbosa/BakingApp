package com.github.ricardosbarbosa.bakingapp.holders;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private final IRecipeViewHolder iRecipeViewHolder;
    public interface IRecipeViewHolder {
        void updateView();
    }
    public final View mView;
    @BindView(R.id.content) public  TextView mContentView;
    @BindView(R.id.bookmark) public ImageView mBookmarkView;

    public Recipe mItem;

    public RecipeViewHolder(View view, IRecipeViewHolder iRecipeViewHolder) {
        super(view);
        this.mView = view;
        this.iRecipeViewHolder = iRecipeViewHolder;
        ButterKnife.bind(this, view);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }

    public void setup(final Recipe recipe) {
        mItem = recipe;
        mContentView.setText(recipe.name);
        updateIcon(recipe);

        mBookmarkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipeUpdated = updateDatabase(recipe);
                updateIcon(recipeUpdated);
                Snackbar.make(v, "Now "+ recipe.name +"recipe's ingredients appear on widget.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                iRecipeViewHolder.updateView();
            }
        });
    }

    private Recipe updateDatabase(Recipe recipe) {
        // 1
        List<Recipe> recipesBookmarked = Recipe.find(Recipe.class, "bookmark = ?", "1");
        for (Recipe r : recipesBookmarked) {
            r.bookmark();
            r.save();
        }

        // 2
        Recipe recipe1 = Recipe.findById(Recipe.class, recipe.getId());
        recipe1.bookmark();
        recipe1.save();

        return recipe1;
    }

    private void updateIcon(Recipe recipe) {
        if (recipe.bookmark)
            mBookmarkView.setImageDrawable(new IconicsDrawable(mView.getContext()).icon(GoogleMaterial.Icon.gmd_bookmark).sizeDp(32));
        else
            mBookmarkView.setImageDrawable(new IconicsDrawable(mView.getContext()).icon(GoogleMaterial.Icon.gmd_bookmark_border).sizeDp(32));
    }
}