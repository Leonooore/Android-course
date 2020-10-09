package com.gmail.elnora.fet.finalcourseproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.StepDataModel;

import java.util.List;

public class TodoStepListAdapter extends RecyclerView.Adapter<TodoStepListAdapter.TodoCheckViewHolder>{

    private List<StepDataModel> stepDataModelList;

    public TodoStepListAdapter(List<StepDataModel> stepDataModelList) {
        this.stepDataModelList = stepDataModelList;
    }

    @NonNull
    @Override
    public TodoStepListAdapter.TodoCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_do_step, parent, false);
        return new TodoStepListAdapter.TodoCheckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoStepListAdapter.TodoCheckViewHolder holder, int position) {
        holder.bind(stepDataModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return stepDataModelList.size();
    }

    public void updateItemList(List<StepDataModel> list) {
        stepDataModelList.clear();
        stepDataModelList.addAll(list);
        notifyDataSetChanged();
    }

    public static class TodoCheckViewHolder extends RecyclerView.ViewHolder {
        private CheckBox viewCheckBoxCookDish;

        public TodoCheckViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCheckBoxCookDish = itemView.findViewById(R.id.viewCheckBoxCookDish);
        }

        private void bind(final StepDataModel stepDataModel) {
            viewCheckBoxCookDish.setText(stepDataModel.getStep());
        }
    }
}
