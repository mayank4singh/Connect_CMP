package com.example.cmplive.Admin.Faculty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmplive.Admin.Notice.NoticeData;
import com.example.cmplive.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter>  {
    private List<TeacherData> list;
    private Context context;





    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;

    }
    public void updateData(List<TeacherData> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent,false);
        return new TeacherViewAdapter(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
            TeacherData item = list.get(position);
            holder.name.setText(item.getName());
            holder.email.setText(item.getEmail());
            holder.post.setText(item.getPost());
            holder.department.setText(item.getDepartment());
            Picasso.get().load(item.getImg()).into(holder.imageView);
            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context instanceof AppCompatActivity){
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        UpdateTeacher updateTeacher = new UpdateTeacher();
                        fragmentTransaction.replace(R.id.container,updateTeacher);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", item.getName());
                        bundle.putString("email", item.getEmail());
                        bundle.putString("post", item.getPost());
                        bundle.putString("img", item.getImg());
                        bundle.putString("pass", item.getPass());
                        bundle.putString("department", item.getDepartment());
                        updateTeacher.setArguments(bundle);


                    }

                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, email, post,department;
        CardView update;
        private ImageView imageView;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            post = itemView.findViewById(R.id.teacherPost);
            update = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.teacherImg);
            department = itemView.findViewById(R.id.teacherDepartemnt);
        }
    }

}
