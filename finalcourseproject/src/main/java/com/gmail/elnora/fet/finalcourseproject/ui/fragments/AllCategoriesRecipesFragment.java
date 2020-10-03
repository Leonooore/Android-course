package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.OnDishTypeClickListener;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.adapter.DishTypeViewAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllCategoriesRecipesFragment extends Fragment {

    public static final String TAG = "AllCategoriesRecipesFragment";
    private static AllCategoriesRecipesFragment instance;

    private List<DishTypeEnum> dishTypeList = new ArrayList<>();
    private OnDishTypeClickListener dishTypeClickListener;
    private DishTypeViewAdapter adapter;
    private SearchView searchView;

    public static AllCategoriesRecipesFragment getInstance() {
        if(instance == null) {
            instance = new AllCategoriesRecipesFragment();
        }
        return instance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDishTypeClickListener) {
            dishTypeClickListener = (OnDishTypeClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        initDishTypeList();
    }

    private void initDishTypeList() {
        for(DishTypeEnum dishTypeEnum : DishTypeEnum.values()) {
            dishTypeList.addAll(Collections.singleton(dishTypeEnum));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_categories_recipes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.viewAllCategoriesRecipesList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (adapter == null) {
            adapter = new DishTypeViewAdapter(dishTypeList, dishTypeClickListener);
        }
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = (SearchView) view.findViewById(R.id.viewSearchCategoryRecipes);
        setSearchViewListener();
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
        dishTypeClickListener = null;
    }

}
