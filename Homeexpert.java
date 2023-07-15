package com.example.wisebridge;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class Homeexpert extends AppCompatActivity {
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private Uri selectedFileUri;
    private static final int REQUEST_FILE_PICKER = 1;
    public static String usernamee;

    //String usertype = getIntent().getStringExtra("usertype");
    EditText title,description,price;
    Spinner userTypeSpinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeexpert);
        title = findViewById(R.id.titlee);
        description = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        userTypeSpinner2 = findViewById(R.id.userTypeSpinner2);
        usernamee = getIntent().getStringExtra("username1");

        storageRef = FirebaseStorage.getInstance("gs://wisebridge-c303a.appspot.com").getReference();
        databaseRef = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/").getReference("Content");
        Button selectFileButton = findViewById(R.id.selectFileButton);
        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });
        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

    }
    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Set the MIME type to allow any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"application/pdf", "video/*"}; // Specify the allowed MIME types
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes); // Set the allowed MIME types

        try {
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_FILE_PICKER);
            Toast.makeText(this, "File has been selected, you can proceed to upload it", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No file picker app found on your device.", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE_PICKER && resultCode == RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.getData();
                // Handle the selected file URI as needed
            }
        }
    }

    private void uploadFile() {
        if (selectedFileUri != null) {
            // Create a unique filename for the uploaded file
            String filename = "file_" + System.currentTimeMillis();

            // Create a reference to the file location in Firebase Storage
            StorageReference fileRef = storageRef.child(filename);
            String userType1 = userTypeSpinner2.getSelectedItem().toString();

            // Upload file to Firebase Storage
            fileRef.putFile(selectedFileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // File upload successful, retrieve the download URL
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Save the download URL to Firebase Realtime Database
                                    String downloadUrl = uri.toString();
                                    String fileId = databaseRef.push().getKey();
                                    databaseRef.child(fileId).child("url").setValue(downloadUrl)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    databaseRef.child(fileId).child("owner").setValue(usernamee);
                                                    databaseRef.child(fileId).child("title").setValue(title.getText().toString().trim());
                                                    databaseRef.child(fileId).child("description").setValue(description.getText().toString().trim());
                                                    databaseRef.child(fileId).child("price").setValue(price.getText().toString().trim());
                                                    databaseRef.child(fileId).child("verify").setValue("0");
                                                    databaseRef.child(fileId).child("subject").setValue(userType1);
                                                    databaseRef.child(fileId).child("keys").setValue(fileId);
                                                    Toast.makeText(Homeexpert.this, "File uploaded!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NotNull Exception e) {
                                                    Toast.makeText(Homeexpert.this, "Failed to save file data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NotNull  Exception e) {
                            Toast.makeText(Homeexpert.this, "Failed to upload file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected!", Toast.LENGTH_SHORT).show();
        }
    }
    private void downloadFile() {
        // Retrieve the download URL from Firebase Realtime Database
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull  DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String downloadUrl = dataSnapshot.getValue(String.class);
                    if (downloadUrl != null) {
                        // Download the file from Firebase Storage
                        StorageReference fileRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUrl);
                        try {
                            File localFile = File.createTempFile("downloaded_file", "");
                            fileRef.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(Homeexpert.this, "File downloaded!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NotNull  Exception e) {
                                            Toast.makeText(Homeexpert.this, "Failed to download file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Homeexpert.this, "No file found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Toast.makeText(Homeexpert.this, "Failed to retrieve file data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
