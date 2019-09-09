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

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder> {

    private List<Article> pinnedArticles;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    HorizontalRecyclerViewAdapter(Activity activity) {
        this.mInflater = LayoutInflater.from(activity);
        this.pinnedArticles = new LinkedList<>();
    }

    public void addItemsToList(Article item){
        this.pinnedArticles.add(0, item); //todo trying
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View horizontalView = mInflater.inflate(R.layout.recyclerview_horizontal, parent,false);
        return new ViewHolder(horizontalView);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article pinnedArticle = pinnedArticles.get(position);
        holder.pinnedArticle = pinnedArticle;
        holder.pinnedTitle.setText(pinnedArticle.getTitle());
        Glide.with(ContexManager.getMainContext()).load(pinnedArticle.getImg()).into(holder.pinnedImageView);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return pinnedArticles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pinnedTitle;
        ImageView pinnedImageView;
        Article pinnedArticle;

        ViewHolder(View horizontalView) {
            super(horizontalView);
            pinnedTitle = itemView.findViewById(R.id.pinnedTitle);
            pinnedImageView = itemView.findViewById(R.id.pinnedImg);
            horizontalView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition(), pinnedArticle);
            }
        }
    }

    // convenience method for getting data at click position
    Article getItem(int id) {
        return pinnedArticles.get(id);
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
