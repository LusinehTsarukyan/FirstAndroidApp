package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Article> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<Article> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void addItemsToList(Article item){
        this.mData.add(0, item);
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
        final Article article = mData.get(position);
        holder.title.setText(article.getTitle());
        holder.pillar.setText(article.getPillarName());
        Glide.with(ContexManager.getMainContext()).load(article.getImg()).into(holder.imageView);
        ViewCompat.setTransitionName(holder.imageView, "transitionName" + position);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mClickListener.onItemClick(holder.getAdapterPosition(), article, holder.imageView);
//            }
//        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView pillar;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            imageView = itemView.findViewById(R.id.img_activity_1);
            pillar = itemView.findViewById(R.id.Pillar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            Intent intent = new Intent(ContexManager.getMainContext(), ArticleActivity.class);
            ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) ContexManager.getMainContext(), imageView, ViewCompat.getTransitionName(view));
            ContexManager.getMainContext().startActivity(intent, option.toBundle());
            Glide.with(ContexManager.getMainContext()).load(imageView).into(ArticleActivity.imageData);
            ArticleActivity.title.setText((CharSequence) title);
        }
    }

    // convenience method for getting data at click position
    Article getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}