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

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.adapter.RecipeListViewAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.RecipesDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.repo.RecipesRepositoryImpl;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class RecipesListDishByCategoryFragment extends Fragment {

    public static final String TAG = "ListRecipesDishCategoryFragment";
    private static final String DISH_TYPE_BUNDLE_KEY = "DISH_TYPE_BUNDLE_KEY";
    private static RecipesListDishByCategoryFragment instance = new RecipesListDishByCategoryFragment();

    private List<RecipeDataModel> recipeDataModelList = new ArrayList<>();
    private RecipeListeners recipeClickListener = null;
    private RecipeListViewAdapter adapter;
    private SearchView searchView = null;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private RecipesDataModelConverter recipesDataModelConverter = new RecipesDataModelConverter();
    private Disposable disposable;

    public static RecipesListDishByCategoryFragment getInstance(DishTypeEnum dishType) {
        Bundle bundle = new Bundle();
        bundle.putString(DISH_TYPE_BUNDLE_KEY, dishType.toString());
        instance.setArguments(bundle);
        return  instance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecipeListeners) {
            recipeClickListener = (RecipeListeners) context;
        }
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
        View view = inflater.inflate(R.layout.fragment_recipes_list_dish_by_category, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.viewRecipesListDishByCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter == null) {
            adapter = new RecipeListViewAdapter(recipeDataModelList, recipeClickListener);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecipeList(view);
        searchView = view.findViewById(R.id.viewSearchDishRecipesByCategory);
        setSearchViewListener();
    }

    private void initRecipeList(View view) {
        String getType = getArguments() != null ? getArguments().getString(DISH_TYPE_BUNDLE_KEY, "") : "";
        disposable = new RecipesRepositoryImpl(okHttpClient, recipesDataModelConverter).getRecipesByType(getType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    recipeDataModelList.addAll(list);
                    adapter.updateItemList(list);
                }, throwable -> {
                    if (throwable.getMessage().contains("Unable to resolve host")) {
                        Snackbar.make(view.findViewById(R.id.viewRecipesListDishByCategory),
                                getString(R.string.error_no_internet_connection),
                                Snackbar.LENGTH_LONG)
                                .show();
                    } else if (throwable.getMessage().contains("402")) {
                        Snackbar.make(view.findViewById(R.id.viewRecipesListDishByCategory),
                                getString(R.string.error_api_request),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                    Log.d("RECIPE_TYPE", throwable.toString());
                });
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
        disposable.dispose();
    }

}
