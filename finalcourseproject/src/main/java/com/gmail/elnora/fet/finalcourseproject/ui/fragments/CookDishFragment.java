package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.adapter.TodoStepListAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.StepDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.StepsDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.repo.RecipesRepositoryImpl;
import com.google.android.material.snackbar.Snackbar;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class CookDishFragment extends Fragment {

    public static final String TAG = "CookDishFragment";
    private static final String RECIPE_ID_BUNDLE_KEY = "RECIPE_ID_BUNDLE_KEY";
    private static CookDishFragment instance;

    private List<StepDataModel> stepDataModelList = new ArrayList<>();
    private TodoStepListAdapter adapter;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private StepsDataModelConverter stepsDataModelConverter = new StepsDataModelConverter();
    private Disposable disposable;

    public static CookDishFragment getInstance(int recipeId) {
        if(instance == null) {
            instance = new CookDishFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID_BUNDLE_KEY, recipeId);
        instance.setArguments(bundle);
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
        View view = inflater.inflate(R.layout.fragment_cook_dish, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getString(R.string.toolbar_text_cook_dish));
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerListToDoCheckBox);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter == null) {
            adapter = new TodoStepListAdapter(stepDataModelList);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTodoCheckList(view);
    }

    private void initTodoCheckList(View view) {
        int getRecipeId = getArguments() != null ? getArguments().getInt(RECIPE_ID_BUNDLE_KEY, 0) : 0;
        disposable = new RecipesRepositoryImpl(okHttpClient, stepsDataModelConverter).getStepsByRecipeId(getRecipeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    stepDataModelList.addAll(list);
                    adapter.updateItemList(list);
                }, throwable -> {
                    if(throwable instanceof UnknownHostException) {
                        Snackbar.make(view.findViewById(R.id.recyclerListToDoCheckBox),
                                getString(R.string.error_no_internet_connection),
                                Snackbar.LENGTH_LONG)
                                .show();
                    } else if (throwable.getMessage().contains("402")) {
                        Snackbar.make(view.findViewById(R.id.recyclerListToDoCheckBox),
                                getString(R.string.error_api_request),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                    Log.d("RECIPE_STEPS", throwable.toString());
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
    }
}
