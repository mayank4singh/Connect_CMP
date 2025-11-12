package com.example.cmplive.Student.Notice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmplive.Admin.Notice.NoticeData;
import com.example.cmplive.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class StNoticeAdapter extends RecyclerView.Adapter<StNoticeAdapter.StNoticeViewAdapter> {

    private List<NoticeData> list;
    private Context context;

    public StNoticeAdapter(List<NoticeData> list, Context context) {
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
    public StNoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.st_newsfeed,parent,false);
        return new StNoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StNoticeViewAdapter holder, int position) {
        NoticeData currentData = list.get(position);
        holder.txt.setText(currentData.getTitle());
        Picasso.get().load(currentData.getImage()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = holder.img.getDrawable();

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StNoticeViewAdapter extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txt;
        public StNoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.deleteNoticeTitle);
            img = itemView.findViewById(R.id.deleteNoticeImg);
        }
    }
}
