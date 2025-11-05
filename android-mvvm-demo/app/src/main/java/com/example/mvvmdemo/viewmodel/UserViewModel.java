package com.example.mvvmdemo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmdemo.model.User;
import com.example.mvvmdemo.repository.UserRepository;

import java.util.List;

/**
 * UserViewModel class
 * Acts as a bridge between the View and Model
 * Manages UI-related data and business logic
 */
public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<List<User>> usersLiveData;
    private MutableLiveData<String> messageLiveData;

    public UserViewModel() {
        userRepository = UserRepository.getInstance();
        usersLiveData = new MutableLiveData<>();
        messageLiveData = new MutableLiveData<>();
    }

    /**
     * Get LiveData for users list
     */
    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }

    /**
     * Get LiveData for messages
     */
    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    /**
     * Load users from repository
     */
    public void loadUsers() {
        // In production, this would be done asynchronously
        // using Coroutines, RxJava, or AsyncTask
        List<User> users = userRepository.getUsers();
        usersLiveData.setValue(users);
        messageLiveData.setValue("成功加载 " + users.size() + " 个用户");
    }

    /**
     * Add a new user
     */
    public void addUser(String name, String email, String phone) {
        int newId = (usersLiveData.getValue() != null)
                    ? usersLiveData.getValue().size() + 1
                    : 1;

        User newUser = new User(newId, name, email, phone);
        boolean success = userRepository.addUser(newUser);

        if (success) {
            // Reload users to update the list
            loadUsers();
            messageLiveData.setValue("用户添加成功");
        } else {
            messageLiveData.setValue("用户添加失败");
        }
    }

    /**
     * Delete a user
     */
    public void deleteUser(int userId) {
        boolean success = userRepository.deleteUser(userId);

        if (success) {
            loadUsers();
            messageLiveData.setValue("用户删除成功");
        } else {
            messageLiveData.setValue("用户删除失败");
        }
    }

    /**
     * Refresh user data
     */
    public void refreshUsers() {
        messageLiveData.setValue("刷新中...");
        loadUsers();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Clean up resources if needed
    }
}
