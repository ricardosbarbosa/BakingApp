package com.github.ricardosbarbosa.bakingapp.activities;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.adapter.IngredientRecyclerViewAdapter;
import com.github.ricardosbarbosa.bakingapp.adapter.RecipeStepRecyclerViewAdapter;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;
import com.github.ricardosbarbosa.bakingapp.model.RecipeStep;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Recipe recipe;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            recipe = getArguments().getParcelable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(recipe.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        loadData(recipe);

        // Show the dummy content as text in a TextView.
        if (recipe != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(recipe.name);
            if (recipe.ingredients != null) {
                RecyclerView ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredients_list);
                ingredientsRecyclerView.setAdapter(new IngredientRecyclerViewAdapter(recipe.ingredients));
            }
            if (recipe.steps != null) {
                RecyclerView stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_list);
                stepsRecyclerView.setAdapter(new RecipeStepRecyclerViewAdapter(recipe.steps));
            }
        }

        return rootView;
    }

    private void loadData(Recipe recipe) {
        this.recipe = Recipe.findById(Recipe.class, recipe.getId());
        this.recipe.ingredients = Ingredient.find(Ingredient.class, "recipe = ?", recipe.getId().toString());
        this.recipe.steps = RecipeStep.find(RecipeStep.class, "recipe = ?", recipe.getId().toString());
    }
}
