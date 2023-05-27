package com.plants.app.info;

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

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {
    private ArrayList<Info> infoList;

    public InfoAdapter(ArrayList<Info> infoList) {
        this.infoList = infoList;
    }

    public class InfoHolder extends RecyclerView.ViewHolder{
        ItemInfoBinding binding;
        ImageView imageView;
        TextView textView;

        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemInfoBinding.bind(itemView);
            imageView = binding.image;
            textView = binding.count;
        }
    }

    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder holder, int position) {
        holder.imageView.setImageResource(infoList.get(position).getImage());
        holder.textView.setText(String.valueOf(infoList.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
