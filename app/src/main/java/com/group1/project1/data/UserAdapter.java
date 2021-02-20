package com.group1.project1.data;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group1.project1.AdminActivity;
import com.group1.project1.AppDatabase;
import com.group1.project1.dao.UserDao;
import com.group1.project1.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> usernames;

    private AppDatabase db;

    public UserAdapter(List<User> list, AppDatabase db){
        this.usernames = list;
        this.db = db;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.inventory_item, viewGroup, false);
        UserAdapter.ViewHolder holder = new UserAdapter.ViewHolder(view);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = usernames.get(position).getUsername();
        Button button = holder.deleteButton;
        holder.getTextView().setText(s);
        button.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                UserDao dao = db.getUserDao();
                dao.delete(usernames.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return usernames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button deleteButton;

        public ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.item_name);
            deleteButton = view.findViewById(R.id.item_select_button);
        }

        public TextView getTextView(){
            return textView;
        }
    }
}
