package com.example.myfirstapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {
    private JSONObject jsonObject;
    private String title;
    private String pillarName;
    private String img;

    Article(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("webTitle");
        this.pillarName = (String) jsonObject.get("pillarName");
        this.img = String.valueOf(jsonObject.getJSONObject("blocks")
                .getJSONObject("main")
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONArray("assets")
                .getJSONObject(0)
                .getString("file"));
    }

    public String getTitle() {
        return title;
    }

    public String getPillarName() {
        return pillarName;
    }
}
