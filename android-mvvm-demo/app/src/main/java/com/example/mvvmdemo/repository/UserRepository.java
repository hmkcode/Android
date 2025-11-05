package com.example.mvvmdemo.repository;

import com.example.mvvmdemo.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * UserRepository class
 * Manages data operations and provides a clean API for data access
 * In a real app, this would interact with local database or remote API
 */
public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {
        // Private constructor for singleton pattern
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    /**
     * Fetch users from data source
     * In production, this would make API calls or database queries
     */
    public List<User> getUsers() {
        // Simulating data fetch with dummy data
        List<User> users = new ArrayList<>();
        users.add(new User(1, "张三", "zhangsan@example.com", "13800138001"));
        users.add(new User(2, "李四", "lisi@example.com", "13800138002"));
        users.add(new User(3, "王五", "wangwu@example.com", "13800138003"));
        users.add(new User(4, "赵六", "zhaoliu@example.com", "13800138004"));
        users.add(new User(5, "钱七", "qianqi@example.com", "13800138005"));
        return users;
    }

    /**
     * Add a new user
     */
    public boolean addUser(User user) {
        // In production, save to database or send to API
        return true;
    }

    /**
     * Delete a user
     */
    public boolean deleteUser(int userId) {
        // In production, delete from database or API
        return true;
    }

    /**
     * Update user information
     */
    public boolean updateUser(User user) {
        // In production, update in database or API
        return true;
    }
}
