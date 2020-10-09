package com.gmail.elnora.fet.finalcourseproject.data.data_converter;

import androidx.room.TypeConverter;

import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipesDataModelConverter {
    @TypeConverter
    public List<RecipeDataModel> fromJsonToRecipeListConverter(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonResultsArray = jsonObject.getJSONArray("results");
        if (jsonResultsArray.length() != 0) {
            List<RecipeDataModel> itemList = new ArrayList<>();
            for (int index = 0; index < jsonResultsArray.length(); index++) {
                JSONObject jsonArrayObject = jsonResultsArray.getJSONObject(index);
                RecipeDataModel recipeDataModel = new RecipeDataModel(
                        jsonArrayObject.getInt("id"),
                        jsonArrayObject.getString("dishTypes"),
                        jsonArrayObject.getString("title"),
                        jsonArrayObject.getString("image"),
                        jsonArrayObject.getString("summary"));
                itemList.add(recipeDataModel);
            }
            return itemList;
        }
        return Collections.emptyList();
    }
}
