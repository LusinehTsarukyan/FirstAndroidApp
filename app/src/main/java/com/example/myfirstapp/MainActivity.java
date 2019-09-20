package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.myfirstapp.ArticlesManager.fileIsEmpty;

public class MainActivity extends AppCompatActivity {
    public static ArticlesManager articlesManager = null;
    public static final String apiKey = "6cb66347-dd59-4b6c-be55-731200528471";
    private ContexManager contexManager;
    private RecyclerView verticalRecyclerView;
    private RecyclerView horizontalRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexManager.setMainContext(this);
        articlesManager = new ArticlesManager(this);
        final String urlString = "https://content.guardianapis.com/search?q=article&show-blocks=all&api-key=" + apiKey;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          ConnectivityManager connectivityManager
                                                  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                          NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                                          if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                                              readFromFile();
                                              runOnUiThread(new Runnable() {
                                                  public void run() {
                                                      articlesManager.adapter.notifyDataSetChanged();
                                                  }
                                              });
                                          } else {
                                              new RequestJsonTask(articlesManager).execute(urlString);
                                          }
                                      }
                                  },
                0, 30000);   // 30000 Millisecond  = 30 second

        // set up the RecyclerView
        horizontalRecyclerView = findViewById(R.id.pinnedArticles);
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(horizontalLinearLayoutManager);

        articlesManager.horizontalRecyclerViewAdapter.setClickListener(new HorizontalRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Article article) {
                ImageView imageView = view.findViewById(R.id.pinnedImg);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("summary", article.getSummary());
                intent.putExtra("id", article.getId());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(), imageView
                        , "img_transition");
                startActivity(intent, option.toBundle());
            }
        });
        horizontalRecyclerView.setAdapter(articlesManager.horizontalRecyclerViewAdapter);


        verticalRecyclerView = findViewById(R.id.articles);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        verticalRecyclerView.setLayoutManager(linearLayoutManager);

        verticalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstItemVisible != 0 && firstItemVisible % articlesManager.adapter.articleList.size() == 0) {
                    recyclerView.getLayoutManager().scrollToPosition(0);
                }
            }
        });

        articlesManager.adapter.setClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Article article) {
                ImageView imageView = view.findViewById(R.id.img_activity_1);
                Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);

                intent.putExtra("imagedata", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("summary", article.getSummary());
                intent.putExtra("id", article.getId());
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) ContexManager.getMainContext(), imageView
                        , "img_transition");
                startActivity(intent, option.toBundle());

                //writing in a file to see articles offline
                if (!articlesManager.savedArticlesIDs.contains(article.getId())) {
                    articlesManager.savedArticlesTitles.add(article.getTitle());
                    articlesManager.savedArticlesCategories.add(article.getPillarName());
                    articlesManager.savedArticlesSummaries.add(article.getSummary());
                    articlesManager.savedArticlesIDs.add(article.getId());

                    saveArrayList(articlesManager.savedArticlesTitles, "titles.txt");
                    saveArrayList(articlesManager.savedArticlesCategories, "categories.txt");
                    saveArrayList(articlesManager.savedArticlesSummaries, "summaries.txt");
                    saveArrayList(articlesManager.savedArticlesIDs, "id.txt");
                }
            }
        });
        verticalRecyclerView.setAdapter(articlesManager.adapter);
    }

    public void saveArrayList(List<String> arrayList, String filename) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(arrayList);
            out.close();
            fileOutputStream.close();
            articlesManager.fileIsEmpty = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSavedArrayList(String filename) {
        ArrayList<String> savedArrayList = null;

        try {
            FileInputStream inputStream = openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            savedArrayList = (ArrayList<String>) in.readObject();
            in.close();
            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedArrayList;
    }


    public void readFromFile() {
        if (!articlesManager.fileIsEmpty) {
            articlesManager.adapter.articleList.clear();
            for (int i = 0; i < articlesManager.savedArticlesTitles.size(); ++i) {
                Article savedArticle = new Article();
                savedArticle.setTitle(getSavedArrayList("titles.txt").get(i));
                savedArticle.setPillarName(getSavedArrayList("categories.txt").get(i));
                savedArticle.setSummary(getSavedArrayList("summaries.txt").get(i));
                savedArticle.setId(getSavedArrayList("id.txt").get(i));
                savedArticle.setImg(null);
                articlesManager.adapter.addItemsToList(savedArticle);
            }
        }
    }
}

