package com.bu.livinggood.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bu.livinggood.bean.Review;
import com.bu.livinggood.databinding.ReviewListItemBinding;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.RVviewHolder> {

// don't need clickable tab
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//    private OnItemClickListener listener;
    private ArrayList<Review> list;

    public ReviewAdapter( ArrayList<Review> list){
//        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public ReviewAdapter.RVviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ReviewListItemBinding listItemsBinding = ReviewListItemBinding.inflate(layoutInflater,parent,false);
//        listItemsBinding.ratingCard.setOnClickListener(view -> {
//            if (listener != null) {
//                listener.onItemClick(view, (int) view.getTag());
//            }
//        });
        return new ReviewAdapter.RVviewHolder(listItemsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.RVviewHolder holder, int position) {
        //update UI based on position
        if(getItemCount()!=0) {
            holder.binding.ratingCard.setTag(position);
            Review review = list.get(position);
            holder.binding.uName.setText(review.getName());
            holder.review.setText(review.getComment());
            holder.binding.reviewScore.setText(String.valueOf(review.getScore()));
            holder.binding.reviewRatingbar.setRating(review.getScore());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 :list.size();
    }

    public static class RVviewHolder extends RecyclerView.ViewHolder {
        private ReviewListItemBinding binding;
        private TextView name, review;
        public RVviewHolder(@NonNull ReviewListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            name = binding.uName;
            review = binding.review;
        }
    }
}
