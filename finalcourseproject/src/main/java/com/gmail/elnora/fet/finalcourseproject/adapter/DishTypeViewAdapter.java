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

import com.gmail.elnora.fet.finalcourseproject.OnDishTypeClickListener;
import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.DishTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class DishTypeViewAdapter extends RecyclerView.Adapter<DishTypeViewAdapter.DishViewHolder> implements Filterable {

    private List<DishTypeEnum> dishTypeList;
    private List<DishTypeEnum> dishTypeFullList;
    private OnDishTypeClickListener dishTypeClickListener;

    public DishTypeViewAdapter(List<DishTypeEnum> dishTypeList, OnDishTypeClickListener dishTypeClickListener) {
        this.dishTypeList = dishTypeList;
        this.dishTypeFullList = new ArrayList<>(dishTypeList);
        this.dishTypeClickListener = dishTypeClickListener;
    }

    @NonNull
    @Override
    public DishTypeViewAdapter.DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish_type, parent, false);
        return new DishTypeViewAdapter.DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        holder.bind(dishTypeList.get(position), dishTypeClickListener);
    }

    @Override
    public int getItemCount() {
        return dishTypeList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<DishTypeEnum> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = dishTypeFullList;
                } else {
                    for (DishTypeEnum dishType : dishTypeFullList) {
                        if (dishType.toString().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(dishType);
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
                dishTypeList.clear();
                dishTypeList.addAll((List<DishTypeEnum>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        private TextView viewDishTypeTitleText;
        private ImageView viewImageDishType;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            viewDishTypeTitleText = itemView.findViewById(R.id.viewDishTypeTitleText);
            viewImageDishType = itemView.findViewById(R.id.viewImageDishType);
        }

        private void bind(final DishTypeEnum dishType, final OnDishTypeClickListener dishTypeClickListener) {
            viewDishTypeTitleText.setText(dishType.toString());
            viewImageDishType.setImageResource(R.drawable.ic_baseline_image_150);
            itemView.setOnClickListener(view -> dishTypeClickListener.onDishTypeClick(dishType));
        }

    }
}
