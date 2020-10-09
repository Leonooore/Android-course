package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.StepDataModel;

import java.util.List;

import io.reactivex.Single;

public interface StepRepository {
    Single<List<StepDataModel>> getStepsByRecipeId(int recipeId);
}
