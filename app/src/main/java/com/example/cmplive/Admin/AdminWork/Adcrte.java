package com.example.cmplive.Admin.AdminWork;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmplive.Chatting.Users;
import com.example.cmplive.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;




public class Adcrte extends Fragment {

   View view;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private ImageView imageView;
    private EditText TeName, TeEmail, TePost, TePass;
    private String Name, Email, Post,Pass, downloadURl ="",Department;
    Button btn;
    private ProgressDialog pd;
    private FirebaseAuth auth;
    private DatabaseReference reference ,database;
    private StorageReference storageRefrence,storage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_adcrte, container, false);
        imageView = view.findViewById(R.id.TeImg);
        TeName = view.findViewById(R.id.TeName);
        TeEmail = view.findViewById(R.id.TeEmail);
        TePost = view.findViewById(R.id.TePost);
        TePass = view.findViewById(R.id.TePass);
        btn = view.findViewById(R.id.TeBtn);
        auth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        storage = FirebaseStorage.getInstance().getReference();

        storageRefrence = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(requireContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
                if (Email.isEmpty() || Pass.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                }else{
                    auth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                //insertData();
                                //StorageReference store = storage.child("Upload").child(uid);
                                pd.setMessage("Uploading...");
                                pd.show();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
                                byte[] finalimg = baos.toByteArray();
                                final StorageReference filepath;
                                filepath = storage.child("Upload").child(uid).child(finalimg+"jpg");
                                final UploadTask uploadTask = filepath.putBytes(finalimg);
                                uploadTask.addOnCompleteListener( new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            downloadURl = String.valueOf(uri);
                                                            insertData();
                                                        }
                                                    });
                                                }
                                            });
                                        }else {
                                            pd.dismiss();
                                            Toast.makeText(requireContext(),"Something Went Wrong", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                });

                                Toast.makeText(requireContext(),"User Has been created",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(),"User not created",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }


        });

        imageView.setOnClickListener((v -> {
            openGallery();
        }));
        return view;
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
    private void checkValidation() {
        Name = TeName.getText().toString();
        Email = TeEmail.getText().toString();
        Post = TePost.getText().toString();
        Pass = TePass.getText().toString();


        if(Name.isEmpty()) {
            TeName.setError("Enter Name");
            TeName.requestFocus();
        } else if (Email.isEmpty()) {
            TeEmail.setError("Enter Email");
            TeEmail.requestFocus();

        }else if (Pass.isEmpty()) {
            TePass.setError("Enter Password");
            TePass.requestFocus();
        }
        else if (Post.isEmpty()) {
            TePost.setError("Enter Post");
            TePost.requestFocus();
        }else if(bitmap == null){
            insertData();
        }else {
            UploadImage();
        }

    }
    private void UploadImage(){
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageRefrence.child("Admin").child(finalimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener( new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadURl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(requireContext(),"Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
    private void insertData(){
        final String uniqueKey = auth.getUid();
        String status = "Hey I'm Using This Application";

        AdminData adminData = new AdminData(Name, Email, Post,Pass, downloadURl);
        Users user = new Users(uniqueKey,Name,Email,Pass,downloadURl,status);
        database.child(uniqueKey).setValue(user);
        reference.child(uniqueKey).setValue(adminData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(requireContext(),"Admin Added", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(requireContext(), "Something Went ", Toast.LENGTH_LONG).show();
            }
        });
    }
}