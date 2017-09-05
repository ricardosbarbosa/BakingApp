package com.github.ricardosbarbosa.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.adapter.RecipeRecyclerViewAdapter;
import com.github.ricardosbarbosa.bakingapp.api.BakingServiceApi;
import com.github.ricardosbarbosa.bakingapp.holders.RecipeViewHolder;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;
import com.github.ricardosbarbosa.bakingapp.model.RecipeStep;
import com.github.ricardosbarbosa.bakingapp.widget.IngredientsWidget;
import com.github.ricardosbarbosa.bakingapp.widget.WidgetService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yalantis.phoenix.PullToRefreshView;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements RecipeViewHolder.IRecipeViewHolder {

    private static final String BASE_URL = "http://go.udacity.com/";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private BakingServiceApi bakingAPI;
    RecyclerView recyclerView;
    PullToRefreshView mPullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = (RecyclerView) findViewById(R.id.recipe_list);
        assert recyclerView != null;

        createAPI();

        setupRecycleView();

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bakingAPI.listRecipes().enqueue(recipesCallback);
                    }
                }, 3);
            }
        });

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void createAPI() {
        GsonBuilder builder = new GsonBuilder();

//        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
//        builder.excludeFieldsWithoutExposeAnnotation();

        Gson gson = builder.setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bakingAPI = retrofit.create(BakingServiceApi.class);
    }

    Callback<List<Recipe>> recipesCallback = new Callback<List<Recipe>>() {
        @Override
        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
            if (response.isSuccessful()) {
                List<Recipe> data = new ArrayList<>();
                data.addAll(response.body());
                saveDatabase(data);
                setupRecycleView();
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
            mPullToRefreshView.setRefreshing(false);
        }

        @Override
        public void onFailure(Call<List<Recipe>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private void setupRecycleView() {
        List<Recipe> recipes = Recipe.listAll(Recipe.class);
        recyclerView.setAdapter(new RecipeRecyclerViewAdapter(recipes, mTwoPane, this));
    }

    private void saveDatabase(List<Recipe> recipes) {
        RecipeStep.deleteAll(RecipeStep.class);
        Ingredient.deleteAll(Ingredient.class);
        Recipe.deleteAll(Recipe.class);

        for (Recipe recipe : recipes) {
            recipe.setId(null);
            recipe.save();
            for (Ingredient ingredient: recipe.ingredients){
                ingredient.recipe = recipe;
                ingredient.save();
            }
            for (RecipeStep step: recipe.steps) {
                step.recipe = recipe;
                step.setId(null);
                step.save();
            }
        }
    }

    @Override
    public void updateView() {
        setupRecycleView();
    }

}
