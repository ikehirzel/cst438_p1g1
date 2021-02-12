package com.group1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	public static final int ACCOUNT_REQUEST_CODE = 1;

	private int userId = -1;

	private TextView greetingView;
	private Button catchButton;
	private Button inventoryButton;
	private Button deleteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// logging into account
		// safeguards against if we happen to start the activity while already logged in
		if (userId == -1) {
			startActivityForResult(new Intent(this, AccountActivity.class), ACCOUNT_REQUEST_CODE);
		}

		// binding objects to interface
		greetingView = findViewById(R.id.greeting_view);
		catchButton = findViewById(R.id.catch_button);
		inventoryButton = findViewById(R.id.inventory_button);
		deleteButton = findViewById(R.id.delete_account_button);

		// TODO set greetingView to say "Hello <username>"

		// if the user pressed the catch pokemon button
		catchButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// if not logged in, do nothing
				if (userId < 0) return;
				Intent gachaIntent = new Intent(view.getContext(), GachaActivity.class);
				// pass in user id so it can add things to user inventory
				gachaIntent.putExtra("id", userId);
				startActivity(gachaIntent);
			}
		});

		inventoryButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// if not logged in, do nothing
				if (userId < 0) return;
				Intent inventoryIntent = new Intent(view.getContext(), InventoryActivity.class);
				// pass in user id so we can see inventory
				inventoryIntent.putExtra("id", userId);
				startActivity(inventoryIntent);
			}
		});

		deleteButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (userId < 0) return;
				// TODO Implement this function
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			// switch statement in case there are more in the future
			switch (requestCode) {

			case ACCOUNT_REQUEST_CODE:
				int id = data.getIntExtra("id", -1);
				if (id == -1) {
					Log.i("MainActivity", "Invalid account info!!!");
					return;
				}

				Log.i("MainActivity", "Got id: " + id);
				break;
			}
		}
	}
}