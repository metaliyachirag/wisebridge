package com.example.wisebridge.ui.home_expert;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wisebridge.databinding.FragmentHomeExpertBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class HomeExpertFragment extends Fragment {

    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private Uri selectedFileUri;
    private static final int REQUEST_FILE_PICKER = 1;
    public static String username;

    EditText title,description,price;
    Spinner userTypeSpinner2;

    private FragmentHomeExpertBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeExpertBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        username = requireActivity().getIntent().getStringExtra("username");
//        String usertype = requireActivity().getIntent().getStringExtra("type");

        userTypeSpinner2 = binding.userTypeSpinner2;
        title = binding.titlee;
        description = binding.desc;
        price = binding.price;
        Button selectFile = binding.selectFileButton;
        Button upload = binding.uploadButton;

        storageRef = FirebaseStorage.getInstance("gs://wisebridge-c303a.appspot.com").getReference();
        databaseRef = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/").getReference("Content");

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        return root;
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"application/pdf", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_FILE_PICKER);
            Toast.makeText(getActivity(), "File has been selected, you can proceed to upload it", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No file picker app found on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
            String filename = "file_" + System.currentTimeMillis();

            StorageReference fileRef = storageRef.child(filename);
            String userType1 = userTypeSpinner2.getSelectedItem().toString();

            fileRef.putFile(selectedFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
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
                                            databaseRef.child(fileId).child("owner").setValue(username);
                                            databaseRef.child(fileId).child("title").setValue(title.getText().toString().trim());
                                            databaseRef.child(fileId).child("description").setValue(description.getText().toString().trim());
                                            databaseRef.child(fileId).child("price").setValue(price.getText().toString().trim());
                                            databaseRef.child(fileId).child("verify").setValue("0");
                                            databaseRef.child(fileId).child("subject").setValue(userType1);
                                            databaseRef.child(fileId).child("keys").setValue(fileId);
                                            Toast.makeText(getActivity(), "File uploaded!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NotNull Exception e) {
                                            Toast.makeText(getActivity(), "Failed to save file data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NotNull  Exception e) {
                    Toast.makeText(getActivity(), "Failed to upload file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("UPLOAD", e.getMessage());
                }
            });
        } else {
            Toast.makeText(getActivity(), "No file selected!", Toast.LENGTH_SHORT).show();
        }
    }
    private void downloadFile() {
        // Retrieve the download URL from Firebase Realtime Database
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
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
                                            Toast.makeText(getActivity(), "File downloaded!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NotNull  Exception e) {
                                            Toast.makeText(getActivity(), "Failed to download file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "No file found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to retrieve file data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}