package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;

public class ViewRecipeFragment extends Fragment {

    public static final String TAG = "ViewRecipeFragment";
    private static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    private static ViewRecipeFragment instance = new ViewRecipeFragment();

    public static ViewRecipeFragment getInstance(RecipeDataModel recipeDataModel) {
        Bundle bundle = new Bundle();
        bundle.putString(RECIPE_BUNDLE_KEY, recipeDataModel.getTitle());
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("RECIPE", getArguments() != null ? getArguments().getString(RECIPE_BUNDLE_KEY, "") : "");
    }

}
