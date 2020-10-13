package com.gmail.elnora.fet.finalcourseproject.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ViewWebRecipeFragment extends Fragment {

    public static final String TAG = "ViewWebRecipeFragment";
    private static final String RECIPE_URL_BUNDLE_KEY = "RECIPE_URL_BUNDLE_KEY";
    private static final String RECIPE_ID_BUNDLE_KEY = "RECIPE_ID_BUNDLE_KEY";
    private static final String RECIPE_TITLE_BUNDLE_KEY = "RECIPE_TITLE_BUNDLE_KEY";
    private static final String RECIPE_IMAGE_BUNDLE_KEY = "RECIPE_IMAGE_BUNDLE_KEY";
    private static final String RECIPE_SUMMARY_BUNDLE_KEY = "RECIPE_SUMMARY_BUNDLE_KEY";
    private static final String RECIPE_TYPE_BUNDLE_KEY = "RECIPE_TYPE_BUNDLE_KEY";

    private static ViewWebRecipeFragment instance = new ViewWebRecipeFragment();
    private RecipeListeners onFabClickListener = null;
    private WebView viewWebViewRecipe;
    private ProgressBar viewProgressBar;
    private FloatingActionButton viewFabAddTodoRecipe;

    public static ViewWebRecipeFragment getInstance(SearchRecipeDataModel searchRecipeDataModel) {
        Bundle bundle = new Bundle();
        bundle.putString(RECIPE_URL_BUNDLE_KEY, searchRecipeDataModel.getSourceUrl());
        bundle.putInt(RECIPE_ID_BUNDLE_KEY, searchRecipeDataModel.getId());
        bundle.putString(RECIPE_TITLE_BUNDLE_KEY, searchRecipeDataModel.getTitle());
        bundle.putString(RECIPE_IMAGE_BUNDLE_KEY, searchRecipeDataModel.getUrlToImage());
        bundle.putString(RECIPE_SUMMARY_BUNDLE_KEY, searchRecipeDataModel.getSummary());
        bundle.putString(RECIPE_TYPE_BUNDLE_KEY, searchRecipeDataModel.getDishTypes());
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
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getArgs(RECIPE_TITLE_BUNDLE_KEY));
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_view_web_recipe, container, false);
        initViews(view);
        initWebView();
        loadWebPageWithUrl(getArgs(RECIPE_URL_BUNDLE_KEY));
        return view;
    }

    private String getArgs(String bundleKey) {
        return getArguments() != null ? getArguments().getString(bundleKey, "") : "";
    }

    private void initViews(View view) {
        viewWebViewRecipe = view.findViewById(R.id.viewWebViewRecipe);
        viewProgressBar = view.findViewById(R.id.viewProgressBar);
        viewFabAddTodoRecipe = view.findViewById(R.id.viewFabAddToDoRecipe);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int recipeId = getArguments() != null ? getArguments().getInt(RECIPE_ID_BUNDLE_KEY, 0) : 0;
        String imageUrl = getArgs(RECIPE_IMAGE_BUNDLE_KEY);
        String title = getArgs(RECIPE_TITLE_BUNDLE_KEY);
        String summary = getArgs(RECIPE_SUMMARY_BUNDLE_KEY);
        String dishType = getArgs(RECIPE_TYPE_BUNDLE_KEY);
        fabAddTodoListClickListener(recipeId, title,imageUrl, summary, dishType);
    }

    private void fabAddTodoListClickListener(int recipeId, String title, String imageUrl, String summary, String dishType) {
        viewFabAddTodoRecipe.setOnClickListener(view -> onFabClickListener.onFabAddTodoListClick(new TodoRecipeEntity(recipeId, title, imageUrl, summary, dishType)));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        viewWebViewRecipe.getSettings().setLoadsImagesAutomatically(true);
        viewWebViewRecipe.getSettings().setJavaScriptEnabled(true);
        viewWebViewRecipe.setWebViewClient(new WebViewRecipeClient());
    }

    private void loadWebPageWithUrl(String url) {
        viewWebViewRecipe.loadUrl(url);
    }

    private class WebViewRecipeClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            viewProgressBar.setVisibility(View.GONE);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            viewProgressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFabClickListener = null;
    }

}
