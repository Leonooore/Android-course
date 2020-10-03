package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.OnRecipeClickListener;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.adapter.RecipeListViewAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesListDishByCategoryFragment extends Fragment {

    public static final String TAG = "ListRecipesDishCategoryFragment";
    private static final String DISH_TYPE_BUNDLE_KEY = "DISH_TYPE_BUNDLE_KEY";
    private static RecipesListDishByCategoryFragment instance = new RecipesListDishByCategoryFragment();

    private List<Recipe> recipeList = new ArrayList<>();
    private OnRecipeClickListener recipeClickListener;
    private RecipeListViewAdapter adapter;
    private SearchView searchView;

    public static RecipesListDishByCategoryFragment getInstance(DishTypeEnum dishType) {
        Bundle bundle = new Bundle();
        bundle.putString(DISH_TYPE_BUNDLE_KEY, dishType.toString());
        instance.setArguments(bundle);
        return  instance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener) {
            recipeClickListener = (OnRecipeClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
        initRecipeList();
    }

    private void initRecipeList() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list_dish_by_category, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.viewRecipesListDishByCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter == null) {
            adapter = new RecipeListViewAdapter(recipeList, recipeClickListener);
        }
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = (SearchView) view.findViewById(R.id.viewSearchDishRecipesByCategory);
        setSearchViewListener();
        Log.d("TYPE", getArguments() != null ? getArguments().getString(DISH_TYPE_BUNDLE_KEY, "") : "");
    }

    private void setSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeClickListener = null;
    }

}
