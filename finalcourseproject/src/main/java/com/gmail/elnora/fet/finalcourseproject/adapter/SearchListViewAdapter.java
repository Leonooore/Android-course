package com.gmail.elnora.fet.finalcourseproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.data.SearchRecipeDataModel;

import java.util.List;

public class SearchListViewAdapter extends RecyclerView.Adapter<SearchListViewAdapter.SearchViewHolder> {

    private List<SearchRecipeDataModel> searchRecipeDataModelList;
    private RecipeListeners recipeClickListener;

    public SearchListViewAdapter(List<SearchRecipeDataModel> searchRecipeDataModelList, RecipeListeners recipeClickListener) {
        this.searchRecipeDataModelList = searchRecipeDataModelList;
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public SearchListViewAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_list, parent, false);
        return new SearchListViewAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewAdapter.SearchViewHolder holder, int position) {
        holder.bind(searchRecipeDataModelList.get(position), recipeClickListener);
    }

    @Override
    public int getItemCount() {
        return searchRecipeDataModelList.size();
    }

    public void updateItemList(List<SearchRecipeDataModel> list) {
        searchRecipeDataModelList.clear();
        searchRecipeDataModelList.addAll(list);
        notifyDataSetChanged();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewImageSearchImagePreview;
        private TextView viewTextViewSearchTitleText;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            viewImageSearchImagePreview = itemView.findViewById(R.id.viewImageSearchImagePreview);
            viewTextViewSearchTitleText = itemView.findViewById(R.id.viewTextViewSearchTitleText);
        }

        private void bind(final SearchRecipeDataModel searchRecipeDataModel, final RecipeListeners recipeClickListener) {
            Glide.with(itemView.getContext())
                    .load(searchRecipeDataModel.getUrlToImage())
                    .error(R.drawable.ic_baseline_image_180)
                    .into(viewImageSearchImagePreview);
            viewTextViewSearchTitleText.setText(searchRecipeDataModel.getTitle());
            itemView.setOnClickListener(view -> recipeClickListener.onSearchedRecipeClick(searchRecipeDataModel));
        }

    }

}
