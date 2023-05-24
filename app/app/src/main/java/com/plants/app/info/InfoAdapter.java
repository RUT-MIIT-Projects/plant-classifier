package com.plants.app.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plants.app.R;
import com.plants.app.databinding.ItemInfoBinding;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ButtonHolder> {
    private ArrayList<Info> infoList;

    public InfoAdapter(ArrayList<Info> infoList) {
        this.infoList = infoList;
    }

    public class ButtonHolder extends RecyclerView.ViewHolder{
        ItemInfoBinding binding;
        ImageView imageView;
        TextView textView;

        public ButtonHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemInfoBinding.bind(itemView);
            imageView = binding.image;
            textView = binding.count;
        }
    }

    @NonNull
    @Override
    public ButtonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new ButtonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonHolder holder, int position) {
        holder.imageView.setImageResource(infoList.get(position).getImage());
        holder.textView.setText(infoList.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
