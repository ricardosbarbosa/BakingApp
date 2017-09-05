package com.github.ricardosbarbosa.bakingapp.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ricardosbarbosa.bakingapp.player.PlayerActivity;
import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.holders.RecipeStepViewHolder;
import com.github.ricardosbarbosa.bakingapp.model.RecipeStep;

import java.util.List;

public class RecipeStepRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipeStepViewHolder> {

    private final List<RecipeStep> mValues;

    public RecipeStepRecyclerViewAdapter(List<RecipeStep> items)  {
        mValues = items;
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_list_content, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeStepViewHolder holder, final int position) {
        holder.setup(mValues.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayerActivity.class);
                intent.setData(Uri.parse(mValues.get(position).videoURL))
                        .putExtra(PlayerActivity.EXTENSION_EXTRA, "mp4")
                        .setAction(PlayerActivity.ACTION_VIEW);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}