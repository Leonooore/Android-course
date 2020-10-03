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
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;

public class RecipesListDishByCategoryFragment extends Fragment {

    public static final String TAG = "ListRecipesDishCategoryFragment";
//    private static final String DISH_TYPE__BUNDLE_KEY = "URL_BUNDLE_KEY";
    private static RecipesListDishByCategoryFragment instance;

    public static RecipesListDishByCategoryFragment getInstance(DishTypeEnum dishType) {
//        Bundle bundle = new Bundle();
//        bundle.putString(DISH_TYPE__BUNDLE_KEY, dishType.getDishType());
//        instance.setArguments(bundle);
        if(instance == null) {
            instance = new RecipesListDishByCategoryFragment();
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
        return inflater.inflate(R.layout.fragment_recipes_list_dish_by_category, container, false);
    }

}
