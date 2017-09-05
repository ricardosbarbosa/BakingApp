package com.github.ricardosbarbosa.bakingapp.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.ricardosbarbosa.bakingapp.R;
import com.github.ricardosbarbosa.bakingapp.model.Ingredient;
import com.github.ricardosbarbosa.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    @BindView(R.id.ingredient) public TextView mIngredientView;
    @BindView(R.id.measure) public TextView mMeasureView;
    @BindView(R.id.quantity) public TextView mQuantityView;

    public Ingredient mItem;

    public IngredientViewHolder(View view) {
        super(view);
        this.mView = view;
        ButterKnife.bind(this, view);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mIngredientView.getText() + "'";
    }

    public void setup(Ingredient ingredient) {
        mItem = ingredient;
        mIngredientView.setText(ingredient.ingredient);
        mMeasureView.setText(ingredient.measure);
        mQuantityView.setText(ingredient.quantity);
    }
}