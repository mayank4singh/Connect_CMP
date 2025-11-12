package com.example.cmplive.Admin.Student;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmplive.Admin.Faculty.TeacherData;
import com.example.cmplive.Admin.Faculty.UpdateTeacher;
import com.example.cmplive.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StAdapter extends RecyclerView.Adapter<StAdapter.StViewAdapter>  {
    private List<StudentData> list;
    private Context context;





    public StAdapter(List<StudentData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void updateData(List<StudentData> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public  StViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.st_item_layout, parent,false);
        return new StViewAdapter(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull StViewAdapter holder, int position) {
        StudentData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.department.setText(item.getDepartment());
        Picasso.get().load(item.getImg()).into(holder.imageView);
       /* holder.update.setOnClickListener(new View.OnClickListener() {
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
                    bundle.putString("img", item.getImg());
                    bundle.putString("pass", item.getPass());
                    bundle.putString("department", item.getDepartment());
                    updateTeacher.setArguments(bundle);


                }

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, email,department;
        CardView update;
        private ImageView imageView;

        public StViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            update = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.teacherImg);
            department = itemView.findViewById(R.id.teacherDepartemnt);
        }
    }

}
