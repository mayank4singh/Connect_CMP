package com.example.cmplive.Student.Teachers;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmplive.Admin.Faculty.TeacherData;
import com.example.cmplive.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class StFcltsAdapter extends RecyclerView.Adapter<StFcltsAdapter.StFcltsViewAdapter>{

    private List<TeacherData> list;
    private Context context;

    public StFcltsAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;

    }
    @NonNull
    @Override
    public StFcltsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.st_faculty_item, parent, false);
        return new StFcltsViewAdapter(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StFcltsViewAdapter holder, int position) {
        TeacherData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        Picasso.get().load(item.getImg()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class StFcltsViewAdapter extends RecyclerView.ViewHolder{
        private TextView name,email,post;
        private ImageView imageView;
        public StFcltsViewAdapter(@NonNull View view){
            super(view);
            name = view.findViewById(R.id.teacherName);
            email = view.findViewById(R.id.teacherEmail);
            post = view.findViewById(R.id.teacherPost);
            imageView = view.findViewById(R.id.teacherImg);
        }

    }
}
