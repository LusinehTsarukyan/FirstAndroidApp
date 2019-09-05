package com.example.myfirstapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {
    private JSONObject jsonObject = null;
    private String title = null;
    private String pillarName;

    Article(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("webTitle");
        this.pillarName = (String) jsonObject.get("pillarName");
    }

    public String getTitle() {
        return title;
    }

    public String getPillarName() {
        return pillarName;
    }
}
