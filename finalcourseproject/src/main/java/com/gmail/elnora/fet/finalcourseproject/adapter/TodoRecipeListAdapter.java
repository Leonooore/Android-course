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
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.RecipeListeners;
import com.gmail.elnora.fet.finalcourseproject.database.TodoRecipeEntity;

import java.util.ArrayList;
import java.util.List;

public class TodoRecipeListAdapter extends RecyclerView.Adapter<TodoRecipeListAdapter.ToDoViewHolder> implements Filterable {

    private List<TodoRecipeEntity> toDoRecipeList;
    private List<TodoRecipeEntity> toDoRecipeFullList;
    private RecipeListeners toDoClickListener;

    public TodoRecipeListAdapter(List<TodoRecipeEntity> toDoRecipeList, RecipeListeners toDoClickListener) {
        this.toDoRecipeList = toDoRecipeList;
        this.toDoRecipeFullList = new ArrayList<>(toDoRecipeList);
        this.toDoClickListener = toDoClickListener;
    }

    @NonNull
    @Override
    public TodoRecipeListAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_do_list, parent, false);
        return new TodoRecipeListAdapter.ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRecipeListAdapter.ToDoViewHolder holder, int position) {
        holder.bind(toDoRecipeList.get(position), toDoClickListener);
    }

    @Override
    public int getItemCount() {
        return toDoRecipeList.size();
    }

    public void updateItemList(List<TodoRecipeEntity> list) {
        toDoRecipeList.clear();
        toDoRecipeList.addAll(list);
        toDoRecipeFullList.clear();
        toDoRecipeFullList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<TodoRecipeEntity> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = toDoRecipeFullList;
                } else {
                    for (TodoRecipeEntity todoRecipeEntity : toDoRecipeFullList) {
                        if (todoRecipeEntity.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(todoRecipeEntity);
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
                toDoRecipeList.clear();
                toDoRecipeList.addAll((List<TodoRecipeEntity>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewImageTodoImagePreview;
        private TextView viewTextViewTodoTitleText;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewImageTodoImagePreview = itemView.findViewById(R.id.viewImageTodoImagePreview);
            viewTextViewTodoTitleText = itemView.findViewById(R.id.viewTextViewTodoTitleText);
        }

        private void bind(final TodoRecipeEntity toDoRecipe, final RecipeListeners recipeClickListener) {
            Glide.with(itemView.getContext())
                    .load(toDoRecipe.getUrlToImage())
                    .placeholder(R.drawable.ic_baseline_image_180)
                    .error(R.drawable.ic_baseline_image_180)
                    .into(viewImageTodoImagePreview);
            viewTextViewTodoTitleText.setText(toDoRecipe.getTitle());
            itemView.setOnClickListener(view -> recipeClickListener.onTodoItemClick(toDoRecipe.getRecipeId()));
        }

    }

}
