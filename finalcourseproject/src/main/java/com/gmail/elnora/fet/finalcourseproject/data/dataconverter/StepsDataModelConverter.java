package com.gmail.elnora.fet.finalcourseproject.data.dataconverter;

import com.gmail.elnora.fet.finalcourseproject.data.StepDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StepsDataModelConverter {
    public List<StepDataModel> fromJsonToRecipeStepListConverter(String jsonData) throws JSONException {
        JSONArray jsonResultsArray = new JSONArray(jsonData);
        if (jsonResultsArray.length() != 0) {
            List<StepDataModel> itemList = new ArrayList<>();
            for (int index = 0; index < jsonResultsArray.length(); index++) {
                JSONObject jsonArrayObject = jsonResultsArray.getJSONObject(index);
                JSONArray jsonStepArray = jsonArrayObject.getJSONArray("steps");
                for (int i = 0; i < jsonStepArray.length(); i++) {
                    JSONObject jsonStepArrayObject = jsonStepArray.getJSONObject(i);
                    String step = jsonStepArrayObject.getInt("number") + ". " + jsonStepArrayObject.getString("step");
                    StepDataModel stepDataModel = new StepDataModel(step);
                    itemList.add(stepDataModel);
                }
            }
            return itemList;
        }
        return Collections.emptyList();
    }
}
