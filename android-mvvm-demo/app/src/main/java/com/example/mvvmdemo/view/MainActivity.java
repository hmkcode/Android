package com.example.mvvmdemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.model.User;
import com.example.mvvmdemo.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * MainActivity - View layer
 * Displays user list using RecyclerView
 * Observes ViewModel for data changes
 */
public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private FloatingActionButton fabRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Setup RecyclerView
        setupRecyclerView();

        // Setup UI components
        setupUI();

        // Observe LiveData from ViewModel
        observeViewModel();

        // Load initial data
        userViewModel.loadUsers();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);
    }

    private void setupUI() {
        fabRefresh = findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.refreshUsers();
            }
        });
    }

    private void observeViewModel() {
        // Observe users list
        userViewModel.getUsersLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    userAdapter.setUsers(users);
                }
            }
        });

        // Observe messages
        userViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null && !message.isEmpty()) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
