package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.R;

public class ViewRecipeFragment extends Fragment {

    public static final String TAG = "ViewRecipeFragment";
    private static ViewRecipeFragment instance;

    public static ViewRecipeFragment getInstance() {
        if(instance == null) {
            instance = new ViewRecipeFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_info, container, false);
    }
}
