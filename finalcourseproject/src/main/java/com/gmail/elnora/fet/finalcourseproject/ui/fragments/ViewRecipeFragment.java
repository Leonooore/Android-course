package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.bumptech.glide.Glide;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.adapter.IngredientsRecipeListAdapter;
import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.data_converter.HtmlConverter;
import com.gmail.elnora.fet.finalcourseproject.data.data_converter.RecipesDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.repo.RecipeRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

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

    private OkHttpClient okHttpClient = new OkHttpClient();
    private RecipesDataModelConverter recipesDataModelConverter = new RecipesDataModelConverter();
    private Disposable disposable;

    private ImageView viewImageViewRecipeImagePreview;
    private TextView viewTextViewRecipeTitleText;
    private TextView viewTextViewRecipeDescriptionText;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
        initIngredientList();
    }

    private void initIngredientList() {
        int getRecipeId = getArguments() != null ? getArguments().getInt(RECIPE_ID_BUNDLE_KEY, 0) : 0;
        disposable = new RecipeRepositoryImpl(okHttpClient, recipesDataModelConverter).getIngredientsByRecipeId(getRecipeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    ingredientDataModelList.addAll(list);
                    adapter.updateItemList(list);
                }, throwable -> Log.d("RECIPE_INGREDIENT", throwable.toString()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.viewListIngredients);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if (adapter == null) {
            adapter = new IngredientsRecipeListAdapter(ingredientDataModelList);
        }
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setViews();
        Log.d("RECIPE_ID", getArgs(RECIPE_ID_BUNDLE_KEY));
    }

    private void initViews(View view) {
        viewImageViewRecipeImagePreview = view.findViewById(R.id.viewImageViewRecipeImagePreview);
        viewTextViewRecipeTitleText = view.findViewById(R.id.viewTextViewRecipeTitleText);
        viewTextViewRecipeDescriptionText = view.findViewById(R.id.viewTextViewRecipeDescriptionText);
    }

    private void setViews() {
        String imageUrl = getArgs(RECIPE_IMAGE_BUNDLE_KEY);
        String title = getArgs(RECIPE_TITLE_BUNDLE_KEY);
        String description = getArgs(RECIPE_SUMMARY_BUNDLE_KEY);

        Glide.with(this).load(imageUrl).into(viewImageViewRecipeImagePreview);
        viewTextViewRecipeTitleText.setText(title);
        viewTextViewRecipeDescriptionText.setText(htmlConverter.convertFromHtml(description));
    }

    private String getArgs(String bundleKey) {
        return getArguments() != null ? getArguments().getString(bundleKey, "") : "";
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
    }

}
