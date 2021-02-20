package com.group1.project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group1.project1.data.UserAdapter;
import com.group1.project1.data.User;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    RecyclerView userRecycler;
    UserAdapter userAdapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userRecycler = findViewById(R.id.user_recycler);

        db = AppDatabase.create(this);

        List<User> users = db.getUserDao().getUsers();

        userAdapter = new UserAdapter(users);
        userRecycler.setAdapter(userAdapter);
        userRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
