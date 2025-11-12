package com.example.cmplive.Admin.Faculty;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmplive.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacher extends Fragment {

    View view;
    private ImageView imageView;
    private EditText name, email, post,pass,department;
    private Button update, delete;
    private String Gname, Gemail, Gpost, Gpass,Gimg,Gdepartment;
    private Bitmap bitmap = null;
    private final int REQ = 1;
    private ProgressDialog pd;
    private StorageReference storageRefrence;
    private DatabaseReference reference, dbRef;
    private String downloadURl, uniqueKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Gname = getArguments().getString("name");
        Gemail = getArguments().getString("email");
        Gpost = getArguments().getString("post");
        Gimg = getArguments().getString("img");
        Gpass = getArguments().getString("pass");
        uniqueKey = getArguments().getString("key");
        Gdepartment = getArguments().getString("department");

        view = inflater.inflate(R.layout.fragment_update_teacher, container, false);

        imageView = view.findViewById(R.id.updateTeacherImg);
        name = view.findViewById(R.id.updateTeacherName);
        email = view.findViewById(R.id.updateTeacherEmail);
        post = view.findViewById(R.id.updateTeacherPost);
        pass = view.findViewById(R.id.updateTeacherPass);
        department = view.findViewById(R.id.updateTeacherDepartment);
        update = view.findViewById(R.id.updateTeacherBtn);
        delete = view.findViewById(R.id.deleteTeacherBtn);
        reference = FirebaseDatabase.getInstance().getReference().child("Teachers");
        storageRefrence = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(requireContext());

        Picasso.get().load(Gimg).into(imageView);
        email.setText(Gemail);
        name.setText(Gname);
        post.setText(Gpost);
        pass.setText(Gpass);
        department.setText(Gdepartment);

        imageView.setOnClickListener((v -> {
            openGallery();
        }));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gname = name.getText().toString();
                Gemail = email.getText().toString();
                Gpost = post.getText().toString();
                Gpass = pass.getText().toString();
                Gdepartment = department.getText().toString();
                checkValidation();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


        return view;
    }

    private void checkValidation() {


        if (Gname.isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
        } else if (Gemail.isEmpty()) {
            email.setError("Enter Email");
            email.requestFocus();
        } else if (Gpost.isEmpty()) {
            post.setError("Enter Post");
            post.requestFocus();
        }
        else if (Gpass.isEmpty()) {
            pass.setError("Enter Post");
            pass.requestFocus();

        } else if (Gdepartment.isEmpty()) {
            department.setError("Enter Department");
            department.requestFocus();

        } else if (bitmap == null) {
            updateData(Gimg);
        } else {
            UploadImage();
        }
    }


    private void openGallery() {
        Intent pickImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImg, REQ);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    private void UploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageRefrence.child("Teachers").child(finalimg + "jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadURl = String.valueOf(uri);
                                    updateData(downloadURl);
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private void updateData(String s) {
        HashMap hashMap = new HashMap();
        hashMap.put("name",Gname);
        hashMap.put("email",Gemail);
        hashMap.put("post",Gpost);
        hashMap.put("pass",Gpass);
        hashMap.put("department",Gdepartment);
        hashMap.put("img",s);

        reference.child(uniqueKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(requireContext(),"Teacher Updated successfully", Toast.LENGTH_LONG).show();
                AdFaculti adFaculti = new AdFaculti();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,adFaculti);
                fragmentTransaction.commit();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),"Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void deleteData(){
        reference.child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(requireContext(),"Teacher deleted successfully", Toast.LENGTH_LONG).show();
                AdFaculti adFaculti = new AdFaculti();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,adFaculti);
                fragmentTransaction.commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(),"Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
