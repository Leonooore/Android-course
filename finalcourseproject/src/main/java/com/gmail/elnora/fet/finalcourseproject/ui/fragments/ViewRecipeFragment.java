package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.bumptech.glide.Glide;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.adapter.IngredientsRecipeListAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.HtmlConverter;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.IngredientsDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;
import com.gmail.elnora.fet.finalcourseproject.repo.RecipesRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class ViewRecipeFragment extends Fragment {

    public static final String TAG = "ViewRecipeFragment";
    private static final String RECIPE_ID_BUNDLE_KEY = "RECIPE_ID_BUNDLE_KEY";
    private static final String RECIPE_TITLE_BUNDLE_KEY = "RECIPE_TITLE_BUNDLE_KEY";
    private static final String RECIPE_IMAGE_BUNDLE_KEY = "RECIPE_IMAGE_BUNDLE_KEY";
    private static final String RECIPE_SUMMARY_BUNDLE_KEY = "RECIPE_SUMMARY_BUNDLE_KEY";

    private static ViewRecipeFragment instance = new ViewRecipeFragment();
    private HtmlConverter htmlConverter = new HtmlConverter();
    private List<IngredientDataModel> ingredientDataModelList = new ArrayList<>();
    private IngredientsRecipeListAdapter adapter;
    private RecipeListeners onFabClickListener = null;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private IngredientsDataModelConverter ingredientsDataModelConverter = new IngredientsDataModelConverter();
    private Disposable disposable;

    private ImageView viewImageViewRecipeImagePreview;
    private TextView viewTextViewRecipeTitleText;
    private TextView viewTextViewRecipeDescriptionText;
    private FloatingActionButton viewFabToDoCook;
    private FloatingActionButton viewFabAddTodoList;

    public static ViewRecipeFragment getInstance(RecipeDataModel recipeDataModel) {
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID_BUNDLE_KEY, recipeDataModel.getId());
        bundle.putString(RECIPE_TITLE_BUNDLE_KEY, recipeDataModel.getTitle());
        bundle.putString(RECIPE_IMAGE_BUNDLE_KEY, recipeDataModel.getUrlToImage());
        bundle.putString(RECIPE_SUMMARY_BUNDLE_KEY, recipeDataModel.getSummary());
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecipeListeners) {
            onFabClickListener = (RecipeListeners) context;
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
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.viewListIngredients);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if (adapter == null) {
            adapter = new IngredientsRecipeListAdapter(ingredientDataModelList);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int recipeId = getArguments() != null ? getArguments().getInt(RECIPE_ID_BUNDLE_KEY, 0) : 0;
        String imageUrl = getArgs(RECIPE_IMAGE_BUNDLE_KEY);
        String title = getArgs(RECIPE_TITLE_BUNDLE_KEY);
        String description = getArgs(RECIPE_SUMMARY_BUNDLE_KEY);

        initIngredientList(recipeId);
        initViews(view);
        setViews(imageUrl, title, description);
        fabTodoCookClickListener(recipeId);
        fabAddTodoListClickListener(recipeId, title,imageUrl);
    }

    private void initIngredientList(int recipeId) {
        disposable = new RecipesRepositoryImpl(okHttpClient, ingredientsDataModelConverter).getIngredientsByRecipeId(recipeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    ingredientDataModelList.addAll(list);
                    adapter.updateItemList(list);
                }, throwable -> Log.d("RECIPE_INGREDIENT", throwable.toString()));
    }

    private void initViews(View view) {
        viewImageViewRecipeImagePreview = view.findViewById(R.id.viewImageViewRecipeImagePreview);
        viewTextViewRecipeTitleText = view.findViewById(R.id.viewTextViewRecipeTitleText);
        viewTextViewRecipeDescriptionText = view.findViewById(R.id.viewTextViewRecipeDescriptionText);
        viewFabToDoCook = view.findViewById(R.id.viewFabToDoCook);
        viewFabAddTodoList = view.findViewById(R.id.viewFabAddTodoList);
    }

    private void setViews(String imageUrl, String title, String description) {
        Glide.with(this).load(imageUrl).into(viewImageViewRecipeImagePreview);
        viewTextViewRecipeTitleText.setText(title);
        viewTextViewRecipeDescriptionText.setText(htmlConverter.convertFromHtml(description));

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void fabTodoCookClickListener(int recipeId) {
        viewFabToDoCook.setOnClickListener(view -> onFabClickListener.onFabTodoCookClick(recipeId));
    }

    private void fabAddTodoListClickListener(int recipeId, String title, String imageUrl) {
        viewFabAddTodoList.setOnClickListener(view -> onFabClickListener.onFabAddTodoListClick(new TodoRecipeEntity(recipeId, title, imageUrl)));
    }

    private String getArgs(String bundleKey) {
        return getArguments() != null ? getArguments().getString(bundleKey, "") : "";
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
        onFabClickListener = null;
    }

}
