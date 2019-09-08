package com.example.myfirstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Article> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Activity mcontext;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Activity context, List<Article> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mcontext = context;
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
        Article article = mData.get(position);
        holder.title.setText(article.getTitle());
        holder.pillar.setText(article.getPillarName());
        holder.summary.setText(article.getSummary());
        Glide.with(ContexManager.getMainContext()).load(article.getImg()).into(holder.imageView);
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
        TextView summary;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            imageView = itemView.findViewById(R.id.img_activity_1);
            pillar = itemView.findViewById(R.id.Pillar);
            summary = itemView.findViewById(R.id.summary);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
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