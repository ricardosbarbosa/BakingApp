package com.github.ricardosbarbosa.bakingapp.api;

import com.github.ricardosbarbosa.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;;

public interface BakingServiceApi {
  @GET("android-baking-app-json")
  Call<List<Recipe>> listRecipes();
}