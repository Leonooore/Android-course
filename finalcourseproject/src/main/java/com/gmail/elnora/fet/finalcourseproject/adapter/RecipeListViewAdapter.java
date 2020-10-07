package com.gmail.elnora.fet.finalcourseproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gmail.elnora.fet.finalcourseproject.OnRecipeClickListener;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.RecipeDataModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeListViewAdapter extends RecyclerView.Adapter<RecipeListViewAdapter.RecipeViewHolder> implements Filterable {

    private List<RecipeDataModel> recipeDataModelList;
    private List<RecipeDataModel> recipeDataModelFullList;
    private OnRecipeClickListener recipeClickListener;

    public RecipeListViewAdapter(List<RecipeDataModel> recipeDataModelList, OnRecipeClickListener recipeClickListener) {
        this.recipeDataModelList = recipeDataModelList;
        this.recipeDataModelFullList = new ArrayList<>(recipeDataModelList);
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipeListViewAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeListViewAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewAdapter.RecipeViewHolder holder, int position) {
        holder.bind(recipeDataModelList.get(position), recipeClickListener);
    }

    @Override
    public int getItemCount() {
        return recipeDataModelList.size();
    }

    public void updateItemList(List<RecipeDataModel> list) {
        recipeDataModelList.clear();
        recipeDataModelList.addAll(list);
        recipeDataModelFullList.clear();
        recipeDataModelFullList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<RecipeDataModel> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = recipeDataModelFullList;
                } else {
                    for (RecipeDataModel recipeDataModel : recipeDataModelFullList) {
                        if (recipeDataModel.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(recipeDataModel);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                recipeDataModelList.clear();
                recipeDataModelList.addAll((List<RecipeDataModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewImageViewRecipeImagePreview;
        private TextView viewTextViewRecipeTitleText;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            viewImageViewRecipeImagePreview = itemView.findViewById(R.id.viewImageViewRecipeImagePreview);
            viewTextViewRecipeTitleText = itemView.findViewById(R.id.viewTextViewRecipeTitleText);
        }

        private void bind(final RecipeDataModel recipeDataModel, final OnRecipeClickListener recipeClickListener) {
            Glide.with(itemView.getContext())
                    .load(recipeDataModel.getUrlToImage())
                    .placeholder(R.drawable.ic_baseline_image_180)
                    .error(R.drawable.ic_baseline_image_180)
                    .into(viewImageViewRecipeImagePreview);
            viewTextViewRecipeTitleText.setText(recipeDataModel.getTitle());
            itemView.setOnClickListener(view -> recipeClickListener.onRecipeClick(recipeDataModel));
        }

    }

}
