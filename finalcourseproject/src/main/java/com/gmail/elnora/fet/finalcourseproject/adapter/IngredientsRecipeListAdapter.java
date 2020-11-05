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
import com.gmail.elnora.fet.finalcourseproject.data.IngredientDataModel;

import java.util.List;

public class IngredientsRecipeListAdapter extends RecyclerView.Adapter<IngredientsRecipeListAdapter.IngredientsViewHolder> {

    private List<IngredientDataModel> ingredientDataModelList;

    public IngredientsRecipeListAdapter(List<IngredientDataModel> ingredientDataModelList) {
        this.ingredientDataModelList = ingredientDataModelList;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientsRecipeListAdapter.IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(ingredientDataModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientDataModelList.size();
    }

    public void updateItemList(List<IngredientDataModel> list) {
        ingredientDataModelList.clear();
        ingredientDataModelList.addAll(list);
        notifyDataSetChanged();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewItemIngredientImage;
        private TextView viewItemTextIngredientName;
        private TextView viewItemTextIngredientAmount;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemIngredientImage = itemView.findViewById(R.id.viewItemIngredientImage);
            viewItemTextIngredientName = itemView.findViewById(R.id.viewItemTextIngredientName);
            viewItemTextIngredientAmount = itemView.findViewById(R.id.viewItemTextIngredientAmount);
        }

        private void bind(final IngredientDataModel ingredientDataModel) {
            Glide.with(itemView.getContext())
                    .load("https://spoonacular.com/cdn/ingredients_100x100/" + ingredientDataModel.getImage())
                    .placeholder(R.drawable.ic_ingredient)
                    .error(R.drawable.ic_ingredient)
                    .into(viewItemIngredientImage);
            viewItemTextIngredientName.setText(ingredientDataModel.getName());
            viewItemTextIngredientAmount.setText(ingredientDataModel.getAmount());
        }

    }
}
