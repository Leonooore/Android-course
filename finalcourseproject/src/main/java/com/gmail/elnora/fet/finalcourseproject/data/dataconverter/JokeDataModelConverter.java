package com.gmail.elnora.fet.finalcourseproject.data.dataconverter;

import com.gmail.elnora.fet.finalcourseproject.data.JokeDataModel;

import org.json.JSONException;
import org.json.JSONObject;

public class JokeDataModelConverter {
    public JokeDataModel fromJsonToJokeConverter(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        return new JokeDataModel(jsonObject.getString("text"));
    }
}
