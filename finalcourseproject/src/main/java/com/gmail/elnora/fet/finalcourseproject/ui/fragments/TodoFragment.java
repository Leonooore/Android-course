package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.adapter.TodoRecipeListAdapter;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;
import com.gmail.elnora.fet.finalcourseproject.viewmodel.TodoRecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {

    public static final String TAG = "TodoFragment";
    private static TodoFragment instance;

    private List<TodoRecipeEntity> todoRecipeEntityList = new ArrayList<>();
    private TodoRecipeListAdapter adapter;
    private RecipeListeners todoClickListener = null;
    private SearchView searchView;

    private TodoRecipeViewModel viewModel;

    public static TodoFragment getInstance() {
        if(instance == null) {
            instance = new TodoFragment();
        }
        return instance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new TodoRecipeViewModel(context);
        if (context instanceof RecipeListeners) {
            todoClickListener = (RecipeListeners) context;
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
        initTodoList();
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);
    }

    private void initTodoList() {
        viewModel.getAllRecipes().observe(this, recipes -> {
            todoRecipeEntityList = recipes;
            adapter.updateItemList(recipes);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        searchView = view.findViewById(R.id.viewSearchToDoRecipes);
        setSearchViewListener();
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.viewRecyclerToDoList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (adapter == null) {
            adapter = new TodoRecipeListAdapter(todoRecipeEntityList, todoClickListener);
        }
        recyclerView.setAdapter(adapter);
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
        todoClickListener = null;
    }

}
