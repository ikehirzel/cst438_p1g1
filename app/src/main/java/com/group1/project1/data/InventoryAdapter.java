package com.group1.project1.data;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group1.project1.InfoActivity;
import com.group1.project1.R;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

	private String[] list;
	private boolean isPokemon;

	public InventoryAdapter(String[] list, boolean isPokemon) {
		this.list = list;
		this.isPokemon = isPokemon;
	}

	@Override public InventoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.inventory_item, parent, false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		String s = list[position];
		TextView textView = holder.textView;
		Button button = holder.selectButton;
		textView.setText(s);
		button.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				String name = list[position];
				Intent intent = new Intent(v.getContext(), InfoActivity.class);
				intent.putExtra("name", list[position]);
				intent.putExtra("pokemon", isPokemon);
				v.getContext().startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return list.length;
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		public TextView textView;
		public Button selectButton;

		public ViewHolder(View view){
			super(view);
			textView = view.findViewById(R.id.item_name);
			selectButton = view.findViewById(R.id.item_select_button);

		}
	}
}
