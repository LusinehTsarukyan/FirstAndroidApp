package com.example.myfirstapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class TestBackgroundTask extends AsyncTask<Void, Void, Void> {
    private static String JSONData = "";
    //using LinkedList<String> for effective concat
    private static List<String> dataParsed = new LinkedList<>();
    private static String singleParsed = "";
    //using HashSet, because only lookup and adding is needed on titleSet
    private static HashSet<String> titlesSet = new HashSet<>();
    public static int isUpdated = 0;
    private static String url = null;
    private static boolean isItFirstTime = true;
    public static int testCount = 0;

    public TestBackgroundTask(String url) {
        this.url = url;
        if (isItFirstTime) {
            try {
                URL url1 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    JSONData = JSONData + line;
                }
                // extractFeatureFromJson
                try {
                    JSONObject baseJsonResponse = new JSONObject(JSONData);
                    JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
                    JSONArray articlesArray = responseJSONObject.getJSONArray("results");
                    for (int i = 0; i < articlesArray.length(); i++) {
                        JSONObject currentArticle = articlesArray.getJSONObject(i);
                        String title = currentArticle.getString("webTitle");
                        titlesSet.add(title); // No sense as it is checking if contains the title anyway
                        this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                                "Category:" + currentArticle.get("pillarName") + "\n";
                        this.dataParsed.add(0, singleParsed);
                        this.dataParsed.add(0, "\n");
                        isItFirstTime = false;
                    }
                } catch (JSONException ex) {
                    Log.println(Log.ERROR, "Failure.", "JSONException");
                    ex.printStackTrace();
                }

            } catch (MalformedURLException e) {
                Log.println(Log.ERROR, "Failure.", "MalformedURLException");
                e.printStackTrace();
            } catch (IOException exception) {
                Log.println(Log.ERROR, "Failure.", "IOException");
                exception.printStackTrace();
            }
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (testCount == 1){
            JSONData = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":117498,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":11750,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-04T04:59:09Z\",\"webTitle\":\"TEST 1\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-27T05:00:12Z\",\"webTitle\":\"Forget a second referendum. Labour has to back revoking article 50 | Zoe Williams\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"type\":\"article\",\"sectionId\":\"film\",\"sectionName\":\"Film\",\"webPublicationDate\":\"2019-06-30T07:00:19Z\",\"webTitle\":\"Article 15 review – no-holds-barred Indian crime thriller\",\"webUrl\":\"https://www.theguardian.com/film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"apiUrl\":\"https://content.guardianapis.com/film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"type\":\"article\",\"sectionId\":\"media\",\"sectionName\":\"Media\",\"webPublicationDate\":\"2019-07-30T06:12:18Z\",\"webTitle\":\"BuzzFeed apologises to Emma Husar for distress caused by 'slut-shaming' article\",\"webUrl\":\"https://www.theguardian.com/media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"apiUrl\":\"https://content.guardianapis.com/media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-22T17:46:15Z\",\"webTitle\":\"Petition to revoke article 50 hits 3.5m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-31T09:56:03Z\",\"webTitle\":\"Article 50 petition to cancel Brexit passes 6m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-07T12:00:47Z\",\"webTitle\":\"The only way to stop the catastrophe of a no-deal Brexit? Revoke article 50 | Jonathan Lis\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-24T23:13:56Z\",\"webTitle\":\"Brexit petition to revoke article 50 exceeds 5m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"type\":\"interactive\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-14T11:39:48Z\",\"webTitle\":\"How did your MP vote on extending article 50?\",\"webUrl\":\"https://www.theguardian.com/politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"apiUrl\":\"https://content.guardianapis.com/politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-22T11:22:17Z\",\"webTitle\":\"May rejects revoke article 50 petition despite 2m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
        }else if(testCount == 2){
            JSONData = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":117498,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":11750,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-04T04:59:09Z\",\"webTitle\":\"Observer article reinforces trans stereotypes | Letters\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-27T05:00:12Z\",\"webTitle\":\"Forget a second referendum. Labour has to back revoking article 50 | Zoe Williams\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"type\":\"article\",\"sectionId\":\"film\",\"sectionName\":\"Film\",\"webPublicationDate\":\"2019-06-30T07:00:19Z\",\"webTitle\":\"Article 15 review – no-holds-barred Indian crime thriller\",\"webUrl\":\"https://www.theguardian.com/film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"apiUrl\":\"https://content.guardianapis.com/film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"type\":\"article\",\"sectionId\":\"media\",\"sectionName\":\"Media\",\"webPublicationDate\":\"2019-07-30T06:12:18Z\",\"webTitle\":\"TEST 2 2\",\"webUrl\":\"https://www.theguardian.com/media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"apiUrl\":\"https://content.guardianapis.com/media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-22T17:46:15Z\",\"webTitle\":\"Petition to revoke article 50 hits 3.5m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-31T09:56:03Z\",\"webTitle\":\"Article 50 petition to cancel Brexit passes 6m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-07T12:00:47Z\",\"webTitle\":\"The only way to stop the catastrophe of a no-deal Brexit? Revoke article 50 | Jonathan Lis\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-24T23:13:56Z\",\"webTitle\":\"Brexit petition to revoke article 50 exceeds 5m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"type\":\"interactive\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-14T11:39:48Z\",\"webTitle\":\"How did your MP vote on extending article 50?\",\"webUrl\":\"https://www.theguardian.com/politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"apiUrl\":\"https://content.guardianapis.com/politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-22T11:22:17Z\",\"webTitle\":\"TEST 2\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}} ";
        }else if(testCount == 3){
            JSONData = " {\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":117498,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":11750,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-04T04:59:09Z\",\"webTitle\":\"Observer article reinforces trans stereotypes | Letters\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/04/observer-article-reinforces-trans-stereotypes-letters\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-27T05:00:12Z\",\"webTitle\":\"TEST 3\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/27/second-referendum-labour-no-deal-revoking-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"type\":\"article\",\"sectionId\":\"film\",\"sectionName\":\"Film\",\"webPublicationDate\":\"2019-06-30T07:00:19Z\",\"webTitle\":\"Article 15 review – no-holds-barred Indian crime thriller\",\"webUrl\":\"https://www.theguardian.com/film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"apiUrl\":\"https://content.guardianapis.com/film/2019/jun/30/article-15-review-angry-indian-crime-thriller\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"type\":\"article\",\"sectionId\":\"media\",\"sectionName\":\"Media\",\"webPublicationDate\":\"2019-07-30T06:12:18Z\",\"webTitle\":\"BuzzFeed apologises to Emma Husar for distress caused by 'slut-shaming' article\",\"webUrl\":\"https://www.theguardian.com/media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"apiUrl\":\"https://content.guardianapis.com/media/2019/jul/30/buzzfeed-apologises-to-emma-husar-for-distress-caused-by-slut-shaming-article\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-22T17:46:15Z\",\"webTitle\":\"Petition to revoke article 50 hits 3.5m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/22/petition-to-revoke-article-50-hits-3-million-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-31T09:56:03Z\",\"webTitle\":\"Article 50 petition to cancel Brexit passes 6m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/31/article-50-petition-to-cancel-brexit-passes-6m-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2019-08-07T12:00:47Z\",\"webTitle\":\"The only way to stop the catastrophe of a no-deal Brexit? Revoke article 50 | Jonathan Lis\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2019/aug/07/stop-catastrophe-no-deal-brexit-revoke-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-24T23:13:56Z\",\"webTitle\":\"Brexit petition to revoke article 50 exceeds 5m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/24/brexit-petition-to-revoke-article-50-reaches-5m-signatures\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"type\":\"interactive\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-14T11:39:48Z\",\"webTitle\":\"How did your MP vote on extending article 50?\",\"webUrl\":\"https://www.theguardian.com/politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"apiUrl\":\"https://content.guardianapis.com/politics/ng-interactive/2019/mar/12/how-did-your-mp-vote-in-the-march-brexit-votes\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2019-03-22T11:22:17Z\",\"webTitle\":\"May rejects revoke article 50 petition despite 2m signatures\",\"webUrl\":\"https://www.theguardian.com/politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"apiUrl\":\"https://content.guardianapis.com/politics/2019/mar/21/petitions-site-crashes-after-thousands-back-call-to-revoke-article-50\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
        }

        extractFeatureFromJson(JSONData);

        return null;
    }

    private void extractFeatureFromJson(String articleJSON) {
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            //Create the JSONObject with the key "response"
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");
            //JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of news stories.
            JSONArray articlesArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentArticle = articlesArray.getJSONObject(i);
                String title = currentArticle.getString("webTitle");
                //String pillarName = currentArticle.getString("pillarName");

                //todo IMAGE

                //adding only new articles
                if (titlesSet.add(title)) {
                    this.singleParsed = "Title:" + currentArticle.get("webTitle") + "\n" +
                            "Category:" + currentArticle.get("pillarName") + "\n";

                    this.dataParsed.add(0, singleParsed);
                    this.dataParsed.add(0, "\n");
                    isUpdated++;
                }
            }
        } catch (JSONException ex) {
            Log.println(Log.ERROR, "Failure.", "JSONException");
            ex.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String viewData = " ";
        for (String str : dataParsed) {
            viewData = viewData + "\n" + str + "\n";
        }
        System.out.println("THIS PARSED " + viewData);

        MainActivity.data.setText(viewData);
    }

}
