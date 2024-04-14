package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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
    }

    private void logout() {
        // Start LoginActivity and finish MainActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


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
        } else {
            Toast.makeText(this, "Please enter an assignment name", Toast.LENGTH_SHORT).show();
        }
    }

    // Toggle completion status of an item when clicked
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
    }

    // Remove completed item when long-clicked
    public void removeCompleted(int position) {
        if (position != RecyclerView.NO_POSITION) {
            completedList.remove(position); // Remove item from the completed list
            completedAdapter.notifyDataSetChanged(); // Update UI
        }
    }
}
