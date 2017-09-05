package com.github.ricardosbarbosa.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy item representing a piece of content.
 */
public class Recipe extends SugarRecord<Recipe> implements Parcelable {
    public String name;
    public Integer serving;
    public String image;
    public boolean bookmark = false;
    public List<Ingredient> ingredients;
    public List<RecipeStep> steps;

    public Recipe() {
    }

    public Recipe(String name, Integer serving, String image, List<Ingredient> ingredients, List<RecipeStep> steps) {
        this.name = name;
        this.serving = serving;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return name;
    }

    protected Recipe(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        name = in.readString();
        serving = in.readByte() == 0x00 ? null : in.readInt();
        image = in.readString();
        bookmark = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<RecipeStep>();
            in.readList(steps, RecipeStep.class.getClassLoader());
        } else {
            steps = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(id);
        }
        dest.writeString(name);
        if (serving == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(serving);
        }
        dest.writeString(image);
        dest.writeByte((byte) (bookmark ? 0x01 : 0x00));
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void bookmark() {
        this.bookmark = !this.bookmark;
    }
}