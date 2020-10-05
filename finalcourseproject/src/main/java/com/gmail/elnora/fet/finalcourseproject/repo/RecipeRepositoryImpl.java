package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.data_converter.RecipesDataModelConverter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeRepositoryImpl implements RecipeRepository {

    private static final String API_KEY = "b02c4852aab443b183f8d791cc2ccace";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private RecipesDataModelConverter recipesDataModelConverter;

    public RecipeRepositoryImpl(OkHttpClient okHttpClient, RecipesDataModelConverter recipesDataModelConverter) {
        this.okHttpClient = okHttpClient;
        this.recipesDataModelConverter = recipesDataModelConverter;
    }

    @Override
    public Single<List<RecipeDataModel>> getRecipesByType(String type) {
        String url = "https://api.spoonacular.com/recipes/complexSearch/?query=" +
                type.toLowerCase() + "&addRecipeInformation=true&apiKey=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        return Single.create((SingleOnSubscribe<String>) emitter -> {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                emitter.onError(new Throwable(String.valueOf(response.code())));
            } else if (response.body() == null) {
                emitter.onError(new Throwable(new NullPointerException("Body is null")));
            } else {
                emitter.onSuccess(response.body().string());
            }
        }).subscribeOn(Schedulers.io())
                .map(jsonData -> recipesDataModelConverter.fromJsonToListConverter(jsonData));
    }

}
