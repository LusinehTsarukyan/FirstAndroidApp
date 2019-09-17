package com.example.myfirstapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Article {
    private JSONObject jsonObject;
    private String id;
    private String title;
    private String pillarName;
    private String img = "https://fbinstantarticles.files.wordpress.com/2016/05/screen_monetization_mobile.jpg";
    private String summary = null;
    private String apiURL;
//    static int test = 0;

    Article(){
        this.title = "Title";
        this.summary = "Summary";
        this.img = null;
        this.pillarName = "Category";
    }

    Article(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        this.title = jsonObject.getString("webTitle");
        this.pillarName = (String) jsonObject.get("pillarName");

        id = jsonObject.getString("id");

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
        this.apiURL = jsonObject.getString("apiUrl") + "?" + "&show-blocks=all&api-key=" + MainActivity.apiKey;
    }

//    public Article(){
//        this.id = "1";
//        this.summary = "Testing new article update.";
//        this.title = "Test!" + test;
//         test++;
//        this.img = "https://fbinstantarticles.files.wordpress.com/2016/05/screen_monetization_mobile.jpg";
//    }

    public String getId() {
        return id;
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

    public String getSummary() {
        //getting summary only when article is clicked, not to save that huge string for all articles
        if (null != this.summary) {
            return summary;
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject baseJsonResponse = new JSONObject(RequestJsonTask.getJSONString(apiURL));
                        JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response").getJSONObject("content");
                        JSONObject body = responseJSONObject
                                .getJSONObject("blocks")
                                .getJSONArray("body")
                                .getJSONObject(0);
                        if (body.getString("bodyTextSummary").equals("")) {
                            summary = title;
                        } else {
                            summary = body.getString("bodyTextSummary");
                        }
                        //only in one case, article doesn't have summary in json. Can be several solutions here.
                        System.out.println(getTitle() + " _____________ " + summary + " API: " + apiURL);
                        if (summary == null) {
                            summary = title + "no summary?";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            while (summary == null) {
                //do nothing wait, not a good idea, thinking another way to solve this
            }
            return summary;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
