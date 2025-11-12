package com.example.cmplive.Admin.AdminWork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmplive.R;
import com.squareup.picasso.Picasso;

import java.net.ContentHandler;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewAdapter> {

    private List<AdminData> list;
    private Context context;

    public AdminAdapter(List<AdminData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_layout, parent,false);
        return new AdminViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewAdapter holder, int position) {
        AdminData data = list.get(position);
        holder.name.setText(data.getName());
        holder.post.setText(data.getPost());
        holder.email.setText(data.getEmail());
        Picasso.get().load(data.getImg()).into(holder.dp);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminViewAdapter extends RecyclerView.ViewHolder{

        TextView name, post, email;
        ImageView dp;

        public AdminViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.adminName);
            post = itemView.findViewById(R.id.adminPost);
            email = itemView.findViewById(R.id.adminEmail);
            dp = itemView.findViewById(R.id.adminImg);
        }
    }
}
