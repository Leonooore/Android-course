package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;

import java.util.List;

import io.reactivex.Single;

public interface TypeRecipesRepository {
    Single<List<RecipeDataModel>> getRecipesByType(String type);
}
