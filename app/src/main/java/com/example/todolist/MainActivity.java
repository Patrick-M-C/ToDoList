package com.example.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.ToDoAdapter;
import com.example.todolist.ToDoItem;
import com.example.todolist.LoginActivity;
import com.example.todolist.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAssignment;
    private Button buttonAdd;
    private RecyclerView recyclerViewOngoing;
    private RecyclerView recyclerViewCompleted;
    private ToDoAdapter ongoingAdapter;
    private ToDoAdapter completedAdapter;
    private List<ToDoItem> ongoingList;
    private List<ToDoItem> completedList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Find views
        editTextAssignment = findViewById(R.id.editText_assignment);
        buttonAdd = findViewById(R.id.button_add);
        recyclerViewOngoing = findViewById(R.id.recyclerView_ongoing);
        recyclerViewCompleted = findViewById(R.id.recyclerView_completed);
        Button buttonLogout = findViewById(R.id.button_logout); // Find the logout button

        // Initialize RecyclerViews
        ongoingList = new ArrayList<>();
        completedList = new ArrayList<>();
        ongoingAdapter = new ToDoAdapter(this, ongoingList);
        completedAdapter = new ToDoAdapter(this, completedList);
        recyclerViewOngoing.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCompleted.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOngoing.setAdapter(ongoingAdapter);
        recyclerViewCompleted.setAdapter(completedAdapter);

        // Set click listener for Add button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssignment();
            }
        });

        // Set click listener for logout button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Retrieve and display saved lists
        retrieveListsFromSharedPreferences();
    }

    // Method to handle logout
    private void logout() {
        // Start LoginActivity and finish MainActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to add a new assignment
    private void addAssignment() {
        String assignmentName = editTextAssignment.getText().toString().trim();
        if (!assignmentName.isEmpty()) {
            // Create a new ToDoItem
            ToDoItem newItem = new ToDoItem(assignmentName, false);

            // Add the new item to the ongoing list
            ongoingList.add(newItem);

            // Notify the adapter that data has been changed
            ongoingAdapter.notifyDataSetChanged();

            // Clear the EditText
            editTextAssignment.setText("");

            // Save lists to SharedPreferences
            saveListsToSharedPreferences();
        } else {
            Toast.makeText(this, "Please enter an assignment name", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to toggle completion status of an item when clicked
    public void toggleCompletion(int position, boolean isCompleted) {
        if (isCompleted) {
            // Move item from ongoing list to completed list
            ToDoItem item = ongoingList.remove(position);
            completedList.add(item);
            completedAdapter.notifyDataSetChanged(); // Update UI for completed tasks
        } else {
            // Move item from completed list to ongoing list
            ToDoItem item = completedList.remove(position);
            ongoingList.add(item);
            ongoingAdapter.notifyDataSetChanged(); // Update UI for ongoing tasks
        }

        // Save lists to SharedPreferences
        saveListsToSharedPreferences();
    }

    // Method to remove completed item when long-clicked
    public void removeCompleted(int position) {
        if (position != RecyclerView.NO_POSITION) {
            completedList.remove(position); // Remove item from the completed list
            completedAdapter.notifyDataSetChanged(); // Update UI

            // Save lists to SharedPreferences
            saveListsToSharedPreferences();
        }
    }

    // Method to save ongoing and completed lists to SharedPreferences
    private void saveListsToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ongoingSize", ongoingList.size());
        for (int i = 0; i < ongoingList.size(); i++) {
            editor.putString("ongoingItemName_" + i, ongoingList.get(i).getName());
            editor.putBoolean("ongoingItemStatus_" + i, ongoingList.get(i).isCompleted());
        }
        editor.putInt("completedSize", completedList.size());
        for (int i = 0; i < completedList.size(); i++) {
            editor.putString("completedItemName_" + i, completedList.get(i).getName());
            editor.putBoolean("completedItemStatus_" + i, completedList.get(i).isCompleted());
        }
        editor.apply();
    }

    // Method to retrieve ongoing and completed lists from SharedPreferences
    private void retrieveListsFromSharedPreferences() {
        int ongoingSize = sharedPreferences.getInt("ongoingSize", 0);
        for (int i = 0; i < ongoingSize; i++) {
            String itemName = sharedPreferences.getString("ongoingItemName_" + i, "");
            boolean itemStatus = sharedPreferences.getBoolean("ongoingItemStatus_" + i, false);
            ongoingList.add(new ToDoItem(itemName, itemStatus));
        }
        ongoingAdapter.notifyDataSetChanged();

        int completedSize = sharedPreferences.getInt("completedSize", 0);
        for (int i = 0; i < completedSize; i++) {
            String itemName = sharedPreferences.getString("completedItemName_" + i, "");
            boolean itemStatus = sharedPreferences.getBoolean("completedItemStatus_" + i, false);
            completedList.add(new ToDoItem(itemName, itemStatus));
        }
        completedAdapter.notifyDataSetChanged();
    }
}