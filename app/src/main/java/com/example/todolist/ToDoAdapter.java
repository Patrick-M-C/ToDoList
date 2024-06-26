package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private Context context;
    private List<ToDoItem> itemList;

    // Constructor to initialize the adapter with context and item list
    public ToDoAdapter(Context context, List<ToDoItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // Create view holder for each item in the list
    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(view);
    }

    // Bind data to the view holder
    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDoItem item = itemList.get(position);
        holder.bind(item);
    }

    // Return the total number of items in the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Delete item from the list at the specified position
    public void deleteItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    // View holder class for each item in the list
    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView itemName;
        private Button buttonDelete;
        private Button buttonComplete; // Add Complete button

        // Constructor to initialize view holder components and listeners
        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.text_item_name);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            buttonComplete = itemView.findViewById(R.id.button_complete); // Initialize Complete button
            itemView.setOnClickListener(this); // Set click listener for item view
            itemView.setOnLongClickListener(this); // Set long click listener for item view

            // Set click listener for delete button
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        deleteItem(position);
                    }
                }
            });

            // Set click listener for Complete button
            buttonComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ToDoItem item = itemList.get(position);
                        item.setCompleted(true); // Mark item as completed
                        itemList.remove(position); // Remove item from ongoing list
                        notifyDataSetChanged(); // Update UI
                    }
                }
            });
        }

        // Bind data to the view holder components
        public void bind(ToDoItem item) {
            itemName.setText(item.getName());

            // Show delete button if task is completed
            if (item.isCompleted()) {
                buttonDelete.setVisibility(View.VISIBLE);
            } else {
                buttonDelete.setVisibility(View.GONE);
            }

            // Set visibility of Complete button based on completion status
            buttonComplete.setVisibility(item.isCompleted() ? View.GONE : View.VISIBLE);
        }

        // Handle item click event to toggle completion status
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ToDoItem item = itemList.get(position);
                item.setCompleted(!item.isCompleted()); // Toggle completion status
                notifyItemChanged(position); // Update UI
            }
        }

        // Handle long click event to remove item from the list
        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                itemList.remove(position); // Remove item from the list
                notifyDataSetChanged(); // Update UI
                return true;
            }
            return false;
        }
    }
}
