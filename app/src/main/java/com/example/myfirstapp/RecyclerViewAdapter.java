package com.example.myfirstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Article> articleList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter(Activity activity) {
        this.mInflater = LayoutInflater.from(activity);
        this.articleList = new LinkedList<>();
    }

    public void addItemsToList(Article item){
        this.articleList.add(0, item);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.article = article;
        holder.title.setText(article.getTitle());
        holder.pillar.setText(article.getPillarName());
        Glide.with(ContexManager.getMainContext()).load(article.getImg()).into(holder.imageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return articleList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView pillar;
        ImageView imageView;
        Article article;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            imageView = itemView.findViewById(R.id.img_activity_1);
            pillar = itemView.findViewById(R.id.Pillar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition(), article);
            }
        }
    }

    // convenience method for getting data at click position
    Article getItem(int id) {
        return articleList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, Article article);
    }
}