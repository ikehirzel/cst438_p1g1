package com.group1.project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.group1.project1.data.InventoryAdapter;
import com.group1.project1.data.User;

public class InventoryActivity extends AppCompatActivity {

	RecyclerView inventoryRecycler;
	InventoryAdapter inventoryAdapter;
	RecyclerView.LayoutManager inventoryManager;
	AppDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);

		inventoryRecycler = findViewById(R.id.inventory_recycler);

		db = AppDatabase.create(this);

		Intent intent = getIntent();
		long userId = intent.getLongExtra("id", -1);
		if (userId == -1) {
			finish();
		}
		boolean pokemon = intent.getBooleanExtra("pokemon", true);

		User user = db.getUserDao().getUser(userId);
		String[] list;
		if (pokemon) {
			list = user.getPokemonList();
		}
		else {
			list = user.getBerryList();
		}
		inventoryAdapter = new InventoryAdapter(list, pokemon);
		inventoryRecycler.setAdapter(inventoryAdapter);
		inventoryRecycler.setLayoutManager(new LinearLayoutManager(this));
	}
}
