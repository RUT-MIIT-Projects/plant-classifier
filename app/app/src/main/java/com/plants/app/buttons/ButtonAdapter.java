package com.plants.app.buttons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plants.app.R;
import com.plants.app.databinding.ItemButtonBinding;
import com.plants.app.fragments.ArticlesFragment;

import java.util.ArrayList;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonHolder> {
    private ArrayList<Button> buttonList;
    private Context context;

    public ButtonAdapter(ArrayList<Button> buttonList, Context context) {
        this.buttonList = buttonList;
        this.context = context;
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
        holder.binding.button.setOnClickListener(view -> ArticlesFragment.broadcast(name, view, context));
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }
}
