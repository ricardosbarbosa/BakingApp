package com.github.ricardosbarbosa.bakingapp;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.github.ricardosbarbosa.bakingapp.activities.RecipeListActivity;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;
import com.github.ricardosbarbosa.bakingapp.model.RecipeStep;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailActivityEspressoTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityRule = new ActivityTestRule(RecipeListActivity.class);

    @Test
    public void verifingIngredients() {

        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Recipe recipe = Recipe.listAll(Recipe.class).get(0);

        List<Ingredient> ingredients = Ingredient.find(Ingredient.class, "recipe = ?", recipe.getId().toString());
        RecyclerViewInteraction.<Ingredient>onRecyclerView(withId(R.id.ingredients_list))
                .withItems(ingredients)
                .check(new RecyclerViewInteraction.ItemViewAssertion<Ingredient>() {
                    @Override
                    public void check(Ingredient item, View view, NoMatchingViewException e) {
                        matches(hasDescendant(withText(item.ingredient)))
                                .check(view, e);
                    }
                });


    }


}

