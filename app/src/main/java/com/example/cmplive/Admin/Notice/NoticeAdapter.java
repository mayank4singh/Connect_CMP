package com.example.cmplive.Admin.Notice;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmplive.Admin.Faculty.TeacherData;
import com.example.cmplive.Admin.Faculty.UpdateTeacher;
import com.example.cmplive.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private ArrayList<NoticeData> list;
    private Context context;

    public NoticeAdapter(ArrayList<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void updateData(List<NoticeData> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {
        NoticeData currentData = list.get(position);
        holder.delTxt.setText(currentData.getTitle());
        Picasso.get().load(currentData.getImage()).into(holder.delImg);
        holder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = holder.delImg.getDrawable();

                // Convert the drawable to a bitmap
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                // Save the bitmap to a file
                String filename = "image.jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    // Notify the user that the image has been downloaded
                    Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notices");
                reference.child(currentData.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Notice Deleted", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Failed to delete notice", Toast.LENGTH_LONG).show();
                    }
                });
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private Button delBtn;
        private TextView delTxt;
        private ImageView delImg;
        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            delBtn = itemView.findViewById(R.id.deleteNotice);
            delTxt = itemView.findViewById(R.id.deleteNoticeTitle);
            delImg = itemView.findViewById(R.id.deleteNoticeImg);
        }
    }
}
