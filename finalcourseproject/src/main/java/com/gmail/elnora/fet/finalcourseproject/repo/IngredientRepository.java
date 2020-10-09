package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;

import java.util.List;

import io.reactivex.Single;

public interface IngredientRepository {
    Single<List<IngredientDataModel>> getIngredientsByRecipeId(int recipeId);
}
