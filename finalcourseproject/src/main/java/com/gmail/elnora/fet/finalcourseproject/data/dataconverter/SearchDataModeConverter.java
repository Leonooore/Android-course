package com.gmail.elnora.fet.finalcourseproject.data.dataconverter;

import android.util.Log;

import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchDataModeConverter {
    public List<SearchRecipeDataModel> fromJsonToRecipeListConverter(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonResultsArray = jsonObject.getJSONArray("results");
        if (jsonResultsArray.length() != 0) {
            List<SearchRecipeDataModel> itemList = new ArrayList<>();
            for (int index = 0; index < jsonResultsArray.length(); index++) {
                JSONObject jsonArrayObject = jsonResultsArray.getJSONObject(index);
                try {
                    SearchRecipeDataModel searchRecipeDataModel = new SearchRecipeDataModel(
                            jsonArrayObject.getInt("id"),
                            jsonArrayObject.getString("title"),
                            jsonArrayObject.getString("image"),
                            jsonArrayObject.getString("sourceUrl"),
                            jsonArrayObject.getString("summary"),
                            jsonArrayObject.getString("dishTypes"));
                    itemList.add(searchRecipeDataModel);
                } catch (JSONException e) {
                    if (e.getMessage().contains("No value for image")) {
                        Log.d("SEARCH_CONVERT", e.getMessage());
                    }
                }
            }
            return itemList;
        }
        return Collections.emptyList();
    }
}
