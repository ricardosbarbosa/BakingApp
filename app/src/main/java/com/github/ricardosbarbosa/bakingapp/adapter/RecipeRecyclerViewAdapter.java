package com.github.ricardosbarbosa.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.activities.RecipeDetailActivity;
import com.github.ricardosbarbosa.bakingapp.activities.RecipeDetailFragment;
import com.github.ricardosbarbosa.bakingapp.holders.RecipeViewHolder;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;

import java.util.List;

public class RecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipeViewHolder> {

    private final List<Recipe> mValues;
    private final RecipeViewHolder.IRecipeViewHolder iRecipeViewHolder;
    private boolean mTwoPane;

    public RecipeRecyclerViewAdapter(List<Recipe> items, boolean mTwoPane, RecipeViewHolder.IRecipeViewHolder iRecipeViewHolder)  {
        mValues = items;
        this.mTwoPane = mTwoPane;
        this.iRecipeViewHolder = iRecipeViewHolder;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
        return new RecipeViewHolder(view, iRecipeViewHolder);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        holder.setup(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(RecipeDetailFragment.ARG_ITEM_ID, holder.mItem);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, holder.mItem);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}