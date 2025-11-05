package com.example.mvvmdemo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * UserAdapter for RecyclerView
 * Displays user list items
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users = new ArrayList<>();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder for user items
     */
    static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvEmail;
        private TextView tvPhone;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }

        public void bind(User user) {
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhone());
        }
    }
}
