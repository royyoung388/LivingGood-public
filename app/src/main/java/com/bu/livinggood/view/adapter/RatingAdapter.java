package com.bu.livinggood.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bu.livinggood.R;
import com.bu.livinggood.bean.RatingSet;
import com.bu.livinggood.databinding.RatingListItemsBinding;

import java.util.Locale;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private int[] images = {R.drawable.security, R.drawable.food, R.drawable.transportation, R.drawable.store};
    private String[] types;
    private Context context;
    private OnItemClickListener listener;
    private RatingSet ratingSet;


    public RatingAdapter(Context context, OnItemClickListener listener, String[] types, RatingSet ratingSet) {
        this.context = context;
        this.listener = listener;
        this.types = types;
        this.ratingSet = ratingSet;
    }

    @NonNull
    @Override
    public RatingAdapter.RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RatingListItemsBinding listItemsBinding = RatingListItemsBinding.inflate(layoutInflater, parent, false);
        listItemsBinding.ratingCard.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(view, (int) view.getTag());
            }
        });
        return new RatingViewHolder(listItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.RatingViewHolder holder, int position) {
        //retrieved data value and update UI
        //get rate
        holder.type_image.setImageResource(images[position]);
        holder.type_txt.setText(types[position]);
        holder.binding.ratingCard.setTag(position);

        // get score string
        String security = String.valueOf(ratingSet.getSecurity_score());
        String food = String.valueOf(ratingSet.getFoods_score());
        String store_score = String.valueOf(ratingSet.getStores_score());
        String transportation_score = String.valueOf(ratingSet.getTransportation_score());
        String[] score_array = {security, food, transportation_score, store_score};

        // get summary string
        String[] summary_array = {
                String.format(Locale.US, "~%d ", ratingSet.getSecurityNum())
                        + context.getString(R.string.rating_summary_security),
                String.format(Locale.US, "~%d ", ratingSet.getFoodNum())
                        + context.getString(R.string.rating_summary_food),
                String.format(Locale.US, "~%d ", ratingSet.getTransitNum())
                        + context.getString(R.string.rating_summary_transit),
                String.format(Locale.US, "~%d ", ratingSet.getStoreNum())
                        + context.getString(R.string.rating_summary_store),
        };

        holder.binding.typescore.setText(score_array[position]);
        holder.binding.typeSummary.setText(summary_array[position]);
        holder.binding.typeRatingbar.setRating(Float.parseFloat(score_array[position]));
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class RatingViewHolder extends RecyclerView.ViewHolder {
        private TextView rating_txt;
        private TextView sum_txt;
        private TextView type_txt;
        private ImageView type_image;
        private RatingListItemsBinding binding;

        public RatingViewHolder(@NonNull RatingListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            rating_txt = binding.typescore;
            sum_txt = binding.typeSummary;
            type_txt = binding.type;
            type_image = binding.typeImage;

            //set onclick listener
        }

        public void bindView(String type, String summary, String rating) {
            //update UI for single item view
            rating_txt.setText(rating);
            sum_txt.setText(summary);
            type_txt.setText(type);
        }
    }
}
