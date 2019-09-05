package com.example.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//public class ArticlesAdapter extends RecyclerView.Adapter {
//    LinkedList<Article> articleList;
//
//    public ArticlesAdapter(LinkedList<Article> articleList) {
//        this.articleList = articleList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
//
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Article article = articleList.get(position);
//
//        holder.title.setText(article.getTitle());
//        holder.category.setText(article.getPillarName());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (articleList != null) {
//            return articleList.size();
//        } else {
//            return 0;
//        }
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public final View view;
//        public final TextView title;
//        public final TextView category;
//        //public final ImageView image;
//
//        public ViewHolder(View view) {
//            super(view);
//            this.view = view;
//            title = view.findViewById(R.id.Title);
//            category = view.findViewById(R.id.Pillar);
//            //image = view.findViewById(R.id.image);
//        }
//    }
//}
