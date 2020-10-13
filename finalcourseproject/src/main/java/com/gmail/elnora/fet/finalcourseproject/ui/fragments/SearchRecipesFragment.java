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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.adapter.SearchListViewAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.RandomRecipesDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.SearchDataModeConverter;
import com.gmail.elnora.fet.finalcourseproject.repo.RecipesRepositoryImpl;
import com.google.android.material.snackbar.Snackbar;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class SearchRecipesFragment extends Fragment {

    public static final String TAG = "SearchRecipesFragment";
    private static SearchRecipesFragment instance;

    private List<SearchRecipeDataModel> searchRecipeDataModelList = new ArrayList<>();
    private RecipeListeners recipeClickListener = null;
    private SearchListViewAdapter adapter;
    private SearchView searchView;
    private String searchQuery = null;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private SearchDataModeConverter searchDataModeConverter = new SearchDataModeConverter();
    private RandomRecipesDataModelConverter randomRecipesDataModelConverter = new RandomRecipesDataModelConverter();
    private Disposable disposable;

    public static SearchRecipesFragment getInstance() {
        if(instance == null) {
            instance = new SearchRecipesFragment();
        }
        return instance;
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
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getString(R.string.toolbar_text_search));
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        return inflater.inflate(R.layout.fragment_search_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        initRandomRecipeList(view);
        searchView = view.findViewById(R.id.viewSearchRecipes);
        setSearchViewListener(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.viewSearchRecipesList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (adapter == null) {
            adapter = new SearchListViewAdapter(searchRecipeDataModelList, recipeClickListener);
        }
        recyclerView.setAdapter(adapter);
    }

    private void initRandomRecipeList(View view) {
        disposable = new RecipesRepositoryImpl(okHttpClient, randomRecipesDataModelConverter).getRandomRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    searchRecipeDataModelList.addAll(list);
                    adapter.updateItemList(list);
                }, throwable -> {
                    if (throwable instanceof UnknownHostException) {
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
                    Log.d("SEARCH", throwable.toString());
                });
    }

    private void setSearchViewListener(View view) {
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = String.valueOf(searchView.getQuery());
                initRecipeList(view, searchQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecipeList(View view, String searchQuery) {
        disposable = new RecipesRepositoryImpl(okHttpClient, searchDataModeConverter).getSearchRecipes(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    searchRecipeDataModelList.addAll(list);
                    adapter.updateItemList(list);
                }, throwable -> {
                    if (throwable instanceof UnknownHostException) {
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
                    Log.d("SEARCH", throwable.toString());
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeClickListener = null;
        disposable.dispose();
    }

}
