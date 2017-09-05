package com.github.ricardosbarbosa.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;
import com.github.ricardosbarbosa.bakingapp.model.RecipeStep;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.List;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        recipe = getIntent().getParcelableExtra(RecipeDetailFragment.ARG_ITEM_ID);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Now the ingredients appears on widget", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                recipe = updateDatabase(recipe);
                updateIcon(fab);
            }
        });

        updateIcon(fab);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(RecipeDetailFragment.ARG_ITEM_ID));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
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

    private void updateIcon(FloatingActionButton fab) {
        if (recipe.bookmark)
            fab.setImageDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_bookmark).color(Color.WHITE).sizeDp(32));
        else
            fab.setImageDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_bookmark_border).color(Color.WHITE).sizeDp(32));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
