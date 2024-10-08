package com.rr.midterm_grocery_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private List<GroceryItem> groceryList;
    private Context context;
    private int selectedPosition = -1;

    public GroceryAdapter(List<GroceryItem> groceryList, Context context) {
        this.groceryList = groceryList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_grocery_item, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        GroceryItem item = groceryList.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition == holder.getBindingAdapterPosition()) {
                selectedPosition = -1;
            } else {
                selectedPosition = holder.getBindingAdapterPosition();
            }
            notifyDataSetChanged();
        });

        switch (item.getCategory()) {
            case "Fruits":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Fruits));
                break;
            case "Dairy":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Dairy));
                break;
            case "Vegetables":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Vegetables));
                break;
            case "Meat":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Meat));
                break;
            case "Beverages":
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Beverages));
                break;
            default:
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.Other));
                break;
        }

        holder.itemView.setAlpha(selectedPosition == holder.getBindingAdapterPosition() ? 0.5f : 1.0f);
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int setSelectedPosition(int pos) {
        return selectedPosition = -1;
    }

    public GroceryItem getItem(int position) {
        return groceryList.get(position);
    }

    public void removeItem(int position) {
        groceryList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, groceryList.size());
    }

    public void updateGroceryList(List<GroceryItem> newGroceryList) {
        groceryList.clear();
        groceryList.addAll(newGroceryList);
        notifyDataSetChanged();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView categoryTextView;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.groceryName);
            categoryTextView = itemView.findViewById(R.id.groceryCategory);
        }

        public void bind(GroceryItem item) {
            nameTextView.setText(item.getName());
            categoryTextView.setText(item.getCategory());
        }
    }
}
