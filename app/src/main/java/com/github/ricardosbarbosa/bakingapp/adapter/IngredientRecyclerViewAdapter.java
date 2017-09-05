package com.github.ricardosbarbosa.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.holders.IngredientViewHolder;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientRecyclerViewAdapter
        extends RecyclerView.Adapter<IngredientViewHolder> {

    private final List<Ingredient> mValues;

    public IngredientRecyclerViewAdapter(List<Ingredient> items)  {
        mValues = items;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_content, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, int position) {
        holder.setup(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}