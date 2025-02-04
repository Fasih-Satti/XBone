package com.example.xbone.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xbone.R;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    private List<Item> itemList;

    public HorizontalAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.nameView.setText(item.getName());
        holder.descriptionView.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameView = itemView.findViewById(R.id.item_name);
            descriptionView = itemView.findViewById(R.id.item_description);
        }
    }
}
