package com.github.ricardosbarbosa.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;

import java.util.List;

/**
 * WidgetService is the {@link RemoteViewsService} that will return our RemoteViewsFactory
 */
public class WidgetService extends RemoteViewsService {
    List<Ingredient> ingredients;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            @Override
            public void onCreate() {
                initData();
            }

            @Override
            public void onDataSetChanged() {
                initData();
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {return ingredients.size();}

            @Override
            public RemoteViews getViewAt(int position) {
                Ingredient ingredient = ingredients.get(position);
                RemoteViews view = new RemoteViews(getApplicationContext().getPackageName(), R.layout.ingredient_list_content_for_widget);

                view.setTextViewText(R.id.ingredient, ingredient.ingredient);
                view.setTextViewText(R.id.measure, ingredient.measure);
                view.setTextViewText(R.id.quantity, ingredient.quantity);

                return view;
            }

            @Override
            public RemoteViews getLoadingView() {return null;}

            @Override
            public int getViewTypeCount() {return 1;}

            @Override
            public long getItemId(int position) { return position; }

            @Override
            public boolean hasStableIds() {return true;}
        };
    }

    private void initData() {
        List<Recipe> recipes = Recipe.find(Recipe.class, "bookmark = ?", "1");
        if (recipes != null && !recipes.isEmpty()) {
            ingredients = Ingredient.find(Ingredient.class, "recipe = ?", recipes.get(0).getId().toString());
        }
    }
}