package com.example.mealmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private List<String> items = new ArrayList<>();
    private Set<String> checkedItems = new HashSet<>();
    private OnGroceryItemClickListener listener;

    public interface OnGroceryItemClickListener {
        void onItemChecked(String item, boolean isChecked);
    }

    public GroceryAdapter(OnGroceryItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<String> items) {
        this.items = items;
        this.checkedItems.retainAll(items); // Keep only items that still exist
        notifyDataSetChanged();
    }

    public void removeCheckedItems() {
        items.removeAll(checkedItems);
        checkedItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class GroceryViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cbItem;
        private final TextView tvItem;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            cbItem = itemView.findViewById(R.id.cbGroceryItem);
            tvItem = itemView.findViewById(R.id.tvGroceryItem);
        }

        public void bind(String item) {
            tvItem.setText(item);
            cbItem.setOnCheckedChangeListener(null);
            cbItem.setChecked(checkedItems.contains(item));
            cbItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    checkedItems.add(item);
                } else {
                    checkedItems.remove(item);
                }
                if (listener != null) {
                    listener.onItemChecked(item, isChecked);
                }
            });
        }
    }
}