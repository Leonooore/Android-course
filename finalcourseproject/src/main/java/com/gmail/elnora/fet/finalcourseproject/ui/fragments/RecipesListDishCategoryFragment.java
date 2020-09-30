package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gmail.elnora.fet.finalcourseproject.R;

public class RecipesListDishCategoryFragment extends Fragment {

    public static final String TAG = "ListRecipesDishCategoryFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes_list_dish_category, container, false);
    }

}
