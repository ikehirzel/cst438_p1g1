package com.group1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final int ACCOUNT_REQUEST_CODE = 1;

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!loggedIn) {
            Intent loginIntent = new Intent(this, AccountActivity.class);
            startActivityForResult(loginIntent, ACCOUNT_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCOUNT_REQUEST_CODE && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("id", -1);
            if (id == -1) {
                Log.i("MainActivity", "Invalid account info!!!");
                return;
            }

            Log.i("MainActivity", "Got id: " + id);

        }
    }
}