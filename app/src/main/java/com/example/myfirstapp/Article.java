package com.example.myfirstapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Article {
    private String title;
    private String pillarName;
    private String img = "https://fbinstantarticles.files.wordpress.com/2016/05/screen_monetization_mobile.jpg";
    private String summary;

    Article(JSONObject jsonObject) throws JSONException {
        this.title = jsonObject.getString("webTitle");
        this.pillarName = (String) jsonObject.get("pillarName");

        JSONObject blocks = jsonObject.getJSONObject("blocks");
        if (blocks.has("main")) {
            JSONArray elements = blocks.getJSONObject("main").getJSONArray("elements");
            if (elements.length() > 0) {
                elements.getJSONObject(0);
                if (elements.getJSONObject(0).has("assets")
                        && elements.getJSONObject(0).getJSONArray("assets").length() > 0) {
                    elements.getJSONObject(0).getJSONArray("assets").getJSONObject(0);
                    if (elements.getJSONObject(0).getJSONArray("assets").getJSONObject(0).has("file")) {
                        img = elements.getJSONObject(0).getJSONArray("assets").getJSONObject(0).getString("file");
                    }
                }
            }
        }

        summary = blocks.getJSONArray("body").getJSONObject(0).getString("bodyTextSummary");

    }

    public String getTitle() {
        return title;
    }

    public String getPillarName() {
        return pillarName;
    }

    public String getImg() {
        return img;
    }

    public String getSummary() {return summary;}
}
