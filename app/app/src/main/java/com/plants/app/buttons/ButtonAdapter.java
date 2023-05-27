package com.plants.app.buttons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.plants.app.R;
import com.plants.app.databinding.ItemButtonBinding;

import java.util.ArrayList;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonHolder> {
    private ArrayList<Button> buttonList;
    private Bundle bundle = new Bundle();

    public ButtonAdapter(ArrayList<Button> buttonList) {
        this.buttonList = buttonList;
    }

    public class ButtonHolder extends RecyclerView.ViewHolder{
        ItemButtonBinding binding;
        ImageView imageView;
        TextView textView;

        public ButtonHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemButtonBinding.bind(itemView);
            imageView = binding.imagePlant;
            textView = binding.name;
        }
    }

    @NonNull
    @Override
    public ButtonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
        return new ButtonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonHolder holder, int position) {
        holder.imageView.setImageResource(buttonList.get(position).getImage());
        holder.textView.setText(buttonList.get(position).getName());

        String name = buttonList.get(position).getName();
        holder.binding.button.setOnClickListener(view -> {
            bundle.putString("Plant", name);
            Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }
}
