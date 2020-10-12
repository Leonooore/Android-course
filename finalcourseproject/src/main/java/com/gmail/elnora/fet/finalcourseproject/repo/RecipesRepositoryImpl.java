package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.JokeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.StepDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.IngredientsDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.JokeDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.RecipesDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.SearchDataModeConverter;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.StepsDataModelConverter;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipesRepositoryImpl implements RecipesRepository {

    private static final String API_KEY = "b02c4852aab443b183f8d791cc2ccace";
    private static final String API_KEY_2 = "dfefec802bca4f6bb311f142862efcc0";

    private OkHttpClient okHttpClient = new OkHttpClient();

    private RecipesDataModelConverter recipesDataModelConverter;
    private IngredientsDataModelConverter ingredientsDataModelConverter;
    private StepsDataModelConverter stepsDataModelConverter;
    private JokeDataModelConverter jokeDataModelConverter;
    private SearchDataModeConverter searchDataModeConverter;

    public RecipesRepositoryImpl(OkHttpClient okHttpClient, RecipesDataModelConverter recipesDataModelConverter) {
        this.okHttpClient = okHttpClient;
        this.recipesDataModelConverter = recipesDataModelConverter;
    }

    public RecipesRepositoryImpl(OkHttpClient okHttpClient, IngredientsDataModelConverter ingredientsDataModelConverter) {
        this.okHttpClient = okHttpClient;
        this.ingredientsDataModelConverter = ingredientsDataModelConverter;
    }

    public RecipesRepositoryImpl(OkHttpClient okHttpClient, StepsDataModelConverter stepsDataModelConverter) {
        this.okHttpClient = okHttpClient;
        this.stepsDataModelConverter = stepsDataModelConverter;
    }

    public RecipesRepositoryImpl(OkHttpClient okHttpClient, JokeDataModelConverter jokeDataModelConverter) {
        this.okHttpClient = okHttpClient;
        this.jokeDataModelConverter = jokeDataModelConverter;
    }

    public RecipesRepositoryImpl(OkHttpClient okHttpClient, SearchDataModeConverter searchDataModeConverter) {
        this.okHttpClient = okHttpClient;
        this.searchDataModeConverter = searchDataModeConverter;
    }

    private Request builtRequest(String url) {
        return new Request.Builder()
                .url(url)
                .addHeader("Range", "bytes=100-1500")
                .build();
    }

    private void createResponse(SingleEmitter<String> emitter, Request request) throws IOException {
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            emitter.onError(new Throwable(String.valueOf(response.code())));
        } else if (response.body() == null) {
            emitter.onError(new Throwable(new NullPointerException("Body is null")));
        } else {
            emitter.onSuccess(response.body().string());
        }
    }

    @Override
    public Single<List<RecipeDataModel>> getRecipesByType(String type) {
        String url = "https://api.spoonacular.com/recipes/complexSearch/?query=" +
                type.toLowerCase() + "&addRecipeInformation=true&apiKey=" + API_KEY;
        Request request = builtRequest(url);
        return Single.create((SingleOnSubscribe<String>) emitter -> createResponse(emitter, request))
                .subscribeOn(Schedulers.io())
                .map(jsonData -> recipesDataModelConverter.fromJsonToRecipeListConverter(jsonData));
    }

    @Override
    public Single<List<IngredientDataModel>> getIngredientsByRecipeId(int recipeId) {
        String url = "https://api.spoonacular.com/recipes/" +  recipeId +
                "/ingredientWidget.json?apiKey=" + API_KEY_2;
        Request request = builtRequest(url);
        return Single.create((SingleOnSubscribe<String>) emitter -> createResponse(emitter, request))
                .subscribeOn(Schedulers.io())
                .map(jsonData -> ingredientsDataModelConverter.fromJsonToIngredientListConverter(jsonData));
    }

    @Override
    public Single<List<StepDataModel>> getStepsByRecipeId(int recipeId) {
        String url = "https://api.spoonacular.com/recipes/" +  recipeId +
                "/analyzedInstructions?apiKey=" + API_KEY_2;
        Request request = builtRequest(url);
        return Single.create((SingleOnSubscribe<String>) emitter -> createResponse(emitter, request))
                .subscribeOn(Schedulers.io())
                .map(jsonData -> stepsDataModelConverter.fromJsonToRecipeStepListConverter(jsonData));
    }

    @Override
    public Single<JokeDataModel> getJoke() {
        String url = "https://api.spoonacular.com/food/jokes/random?apiKey=" + API_KEY_2;
        Request request = builtRequest(url);
        return Single.create((SingleOnSubscribe<String>) emitter -> createResponse(emitter, request))
                .subscribeOn(Schedulers.io())
                .map(jsonData -> jokeDataModelConverter.fromJsonToJokeConverter(jsonData));
    }

    @Override
    public Single<List<SearchRecipeDataModel>> getSearchRecipes(String searchQuery) {
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + searchQuery + "&addRecipeInformation=true&apiKey=" + API_KEY;
        Request request = builtRequest(url);
        return Single.create((SingleOnSubscribe<String>) emitter -> createResponse(emitter, request))
                .subscribeOn(Schedulers.io())
                .map(jsonData -> searchDataModeConverter.fromJsonToRecipeListConverter(jsonData));
    }

}
