package com.gmail.elnora.fet.finalcourseproject.repo;

import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;
import com.gmail.elnora.fet.finalcourseproject.data.data_converter.IngredientsDataModelConverter;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IngredientRepositoryImpl implements IngredientRepository {

    private static final String API_KEY = "dfefec802bca4f6bb311f142862efcc0";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private IngredientsDataModelConverter ingredientsDataModelConverter;

    public IngredientRepositoryImpl(OkHttpClient okHttpClient, IngredientsDataModelConverter ingredientsDataModelConverter) {
        this.okHttpClient = okHttpClient;
        this.ingredientsDataModelConverter = ingredientsDataModelConverter;
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
    public Single<List<IngredientDataModel>> getIngredientsByRecipeId(int recipeId) {
        String url = "https://api.spoonacular.com/recipes/" +  recipeId +
                "/ingredientWidget.json?apiKey=" + API_KEY;
        Request request = builtRequest(url);
        return Single.create((SingleOnSubscribe<String>) emitter -> createResponse(emitter, request))
                .subscribeOn(Schedulers.io())
                .map(jsonData -> ingredientsDataModelConverter.fromJsonToIngredientListConverter(jsonData));
    }
}
