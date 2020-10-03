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

import com.gmail.elnora.fet.finalcourseproject.OnRecipeClickListener;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListViewAdapter extends RecyclerView.Adapter<RecipeListViewAdapter.RecipeViewHolder> implements Filterable {

    private List<Recipe> recipeList;
    private List<Recipe> recipeFullList;
    private OnRecipeClickListener recipeClickListener;

    public RecipeListViewAdapter(List<Recipe> recipeList, OnRecipeClickListener recipeClickListener) {
        this.recipeList = recipeList;
        this.recipeFullList = new ArrayList<>(recipeList);
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
        holder.bind(recipeList.get(position), recipeClickListener);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Recipe> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = recipeFullList;
                } else {
                    for (Recipe recipe : recipeFullList) {
                        if (recipe.getName().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(recipe);
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
                recipeList.clear();
                recipeList.addAll((List<Recipe>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewImageViewRecipeImagePreview;
        private TextView viewTextViewRecipeTitleText;
        private TextView viewTextViewRecipeDescriptionText;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            viewImageViewRecipeImagePreview = itemView.findViewById(R.id.viewImageViewRecipeImagePreview);
            viewTextViewRecipeTitleText = itemView.findViewById(R.id.viewTextViewRecipeTitleText);
            viewTextViewRecipeDescriptionText = itemView.findViewById(R.id.viewTextViewRecipeDescriptionText);
        }

        private void bind(final Recipe recipe, final OnRecipeClickListener recipeClickListener) {
            viewImageViewRecipeImagePreview.setImageResource(R.drawable.ic_baseline_image_170);
            viewTextViewRecipeTitleText.setText(recipe.getName());

            itemView.setOnClickListener(view -> recipeClickListener.onRecipeClick(recipe));
        }

    }

}
