package com.example.cmplive.Admin.Student;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cmplive.Admin.Faculty.TeacherData;
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




public class AddStudent extends Fragment {

    private final int REQ = 1;
    private Bitmap bitmap = null;
    View view;
    private ImageView imageView;
    private EditText TeName, TeEmail, TePass;
    private Spinner category;
    private Button btn;
    private String TeCategory;
    private String Name, Email,Pass, downloadURl ="",Department;
    private ProgressDialog pd;
    private FirebaseAuth auth;
    private DatabaseReference reference , database;
    private StorageReference storageRefrence, storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_student, container, false);
        imageView = view.findViewById(R.id.TeImg);
        TeName = view.findViewById(R.id.TeName);
        TeEmail = view.findViewById(R.id.TeEmail);
        TePass = view.findViewById(R.id.TePass);
        category = view.findViewById(R.id.TeAdd);
        btn = view.findViewById(R.id.TeBtn);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        storageRefrence = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(requireContext());


        String[] items = new String[]{"Select Category", "BCA", "BCA+MCA", "MCA", "O LEVEL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);

        // Set the adapter to the Spinner
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TeCategory = category.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imageView.setOnClickListener((v -> {
            openGallery();
        }));

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
                                insertData();
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
        Pass = TePass.getText().toString();
        Department = TeCategory;

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
        else if (category.equals("Select Category")){
            Toast.makeText(requireContext(),"Please Provide Student Category",Toast.LENGTH_LONG).show();
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
        filepath = storageRefrence.child("Teachers").child(finalimg+"jpg");
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
        StudentData studentData = new StudentData(Name, Email,Pass, downloadURl,Department);
        Users user = new Users(uniqueKey,Name,Email,Pass,downloadURl,status);
        database.child(uniqueKey).setValue(user);
        reference.child(uniqueKey).setValue(studentData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(requireContext(),"Student Added", Toast.LENGTH_LONG).show();
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
