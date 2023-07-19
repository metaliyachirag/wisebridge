package com.example.wisebridge;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Homeuser extends AppCompatActivity {
    public static String selectedItem;
    public static DatabaseReference databaseReference,databaseReference1,databaseRef,re;
    private StorageReference storageRef;
    public static String fileUrl, fileName;
    public static String yy;

    Button logout;
    Spinner userTypeSpinnerad;
    public static RecyclerView postRecyclerView;
    public static contentsAdaptor contentsAdaptors;
    public static List<contents> contentsz = new ArrayList<>();
    public static String username2;
    String userInfo = "user";



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeuser);
        logout = findViewById(R.id.logoutadmin1);
        userTypeSpinnerad = findViewById(R.id.userTypeSpinnerad1);
        username2 = getIntent().getStringExtra("username2");

        //System.out.println(username2);
        //Toast.makeText(Homeuser.this, "Helloooo "+username2, Toast.LENGTH_SHORT).show();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homeuser.this, Login.class);
                startActivity(intent);
            }
        });


        postRecyclerView = findViewById(R.id.postRecyclerView1);
        contentsAdaptors = new contentsAdaptor(contentsz,userInfo);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userTypeSpinnerad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                contentsz.clear();

                 selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(Adminhome.this, "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();

                if(selectedItem.equals("Acc")){

                    //Button subscribeButton = findViewById(R.id.Subscribe);
                    //subscribeButton.setText("Verify");
                    postRecyclerView.setAdapter(contentsAdaptors);
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Content");
                    contentsz.clear();

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(childSnapshot.child("subscribers").child(username2).exists()){

                                }
                                else{
                                    if(childSnapshot.child("subject").getValue().toString().equals("Acc")){
                                        String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                        String id = childSnapshot.child("owner").getValue().toString().trim();
                                        String price = childSnapshot.child("price").getValue().toString().trim();
                                        String descr = childSnapshot.child("description").getValue().toString().trim();
                                        String ver = childSnapshot.child("verify").getValue().toString();
                                        String keys = childSnapshot.child("keys").getValue().toString();
                                        contents content = new contents(name, id,descr,price,keys); // Create a new Post object
                                        contentsz.add(content);
                                    }
                                }






                            }
                            contentsAdaptors.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });

                }
                else if(selectedItem.equals("Elective")){
                    //Button subscribeButton = findViewById(R.id.Subscribe);
                    //subscribeButton.setText("Verify");
                    postRecyclerView.setAdapter(contentsAdaptors);
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Content");
                    contentsz.clear();

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(childSnapshot.child("subscribers").child(username2).exists()){

                                }
                                else {

                                    if (childSnapshot.child("subject").getValue().toString().equals("Elective")) {
                                        String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                        String id = childSnapshot.child("owner").getValue().toString().trim();
                                        String price = childSnapshot.child("price").getValue().toString().trim();
                                        String descr = childSnapshot.child("description").getValue().toString().trim();
                                        String ver = childSnapshot.child("verify").getValue().toString();
                                        String keys = childSnapshot.child("keys").getValue().toString();
                                        contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                        contentsz.add(content);
                                    }
                                }




                            }
                            contentsAdaptors.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });

                }
                else if(selectedItem.equals("Ase")){
                    //Button subscribeButton = findViewById(R.id.Subscribe);
                    //subscribeButton.setText("Verify");
                    postRecyclerView.setAdapter(contentsAdaptors);
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Content");
                    contentsz.clear();

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(childSnapshot.child("subscribers").child(username2).exists()){

                                }
                                else {

                                    if (childSnapshot.child("subject").getValue().toString().equals("Ase")) {
                                        String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                        String id = childSnapshot.child("owner").getValue().toString().trim();
                                        String price = childSnapshot.child("price").getValue().toString().trim();
                                        String descr = childSnapshot.child("description").getValue().toString().trim();
                                        String ver = childSnapshot.child("verify").getValue().toString();
                                        String keys = childSnapshot.child("keys").getValue().toString();
                                        contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                        contentsz.add(content);
                                    }
                                }




                            }
                            contentsAdaptors.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }


        });

        contentsAdaptors.setOnItemClickListener(new contentsAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(contents content) {
                String keys = content.getKeys();
                String ti=content.getName();
                //Toast.makeText(getApplicationContext(), keys, Toast.LENGTH_SHORT).show();
                FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                databaseReference1 = ref1.getReference().child("Content").child(keys).child("subscribers").child(username2);
               // databaseReference1.child(keys).child("subscribers").child(username2).setValue(username2);
                //Toast.makeText(Homeuser.this,"set value of subscriber "+  databaseReference1.child(keys).child("subscribers").child(username2).getKey(),Toast.LENGTH_SHORT).show();



                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText((getApplicationContext()), "Already subscribed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final String[] yt = new String[1];
                            databaseReference1.child(username2).setValue(keys);
                            //downloadFile(keys);
                            Activity activity = Homeuser.this;

                            final String[] x = {"gs://wisebridge-c303a.appspot.com"};
                            re  = ref1.getReference().child("Content").child(keys).child("url");

                            re.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                          yt[0] = dataSnapshot.getValue().toString().trim();
                                       //fileUrl="https://firebasestorage.googleapis.com/v0/b/wisebridge-c303a.appspot.com/o/file_1689397367701?alt=media&token=b893d4c3-d16b-4086-878d-aefe20d7396c";
                                       fileUrl = yt[0];
                                        Log.d("Tag", fileUrl);
                                        Log.d("Tag", yt[0]);
                                        if(yt[0].equals(fileUrl)){Toast.makeText(Homeuser.this,yt[0],Toast.LENGTH_SHORT).show();}
                                        //if(y.equals(fileUrl)){

//                                Log.d("1",y);
                                        //}
                                        fileName = ti+"_"+keys+".pdf";
                                        if (hasWriteExternalStoragePermission(activity)) {
                                            downloadedFile(getApplicationContext(), fileUrl, fileName);
                                        }
                                        else {
                                            // Request the permission
                                            int requestCode = 1; // Choose any suitable request code
                                            requestWriteExternalStoragePermission(activity, requestCode);
                                        }
                                        //Toast.makeText(Homeuser.this,y,Toast.LENGTH_SHORT).show();

                                        //fileUrl = y;
                                        //Toast.makeText(Homeuser.this,fileUrl,Toast.LENGTH_SHORT).show();
                                        // Use the 'url' value here
                                    }
                                }

                                @Override
                                public void onCancelled(@NotNull DatabaseError databaseError) {
                                    // Handle the error if any
                                }
                            });





                           // Toast.makeText((getApplicationContext()), "Subscribed now", Toast.LENGTH_SHORT).show();
                            contentsz.clear();

                            //selectedItem = parent.getItemAtPosition(position).toString();
                            //Toast.makeText(Adminhome.this, "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();

                            if(selectedItem.equals("Acc")){

                                //Button subscribeButton = findViewById(R.id.Subscribe);
                                //subscribeButton.setText("Verify");
                                postRecyclerView.setAdapter(contentsAdaptors);
                                FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                                databaseReference1 = ref1.getReference().child("Content");
                                contentsz.clear();

                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            if(childSnapshot.child("subscribers").child(username2).exists()){

                                            }
                                            else{
                                                if(childSnapshot.child("subject").getValue().toString().equals("Acc")){
                                                    String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                                    String id = childSnapshot.child("owner").getValue().toString().trim();
                                                    String price = childSnapshot.child("price").getValue().toString().trim();
                                                    String descr = childSnapshot.child("description").getValue().toString().trim();
                                                    String ver = childSnapshot.child("verify").getValue().toString();
                                                    String keys = childSnapshot.child("keys").getValue().toString();
                                                    contents content = new contents(name, id,descr,price,keys); // Create a new Post object
                                                    contentsz.add(content);
                                                }
                                            }






                                        }
                                        contentsAdaptors.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                                });

                            }
                            else if(selectedItem.equals("Elective")){
                                //Button subscribeButton = findViewById(R.id.Subscribe);
                                //subscribeButton.setText("Verify");
                                postRecyclerView.setAdapter(contentsAdaptors);
                                FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                                databaseReference1 = ref1.getReference().child("Content");
                                contentsz.clear();

                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            if(childSnapshot.child("subscribers").child(username2).exists()){

                                            }
                                            else {

                                                if (childSnapshot.child("subject").getValue().toString().equals("Elective")) {
                                                    String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                                    String id = childSnapshot.child("owner").getValue().toString().trim();
                                                    String price = childSnapshot.child("price").getValue().toString().trim();
                                                    String descr = childSnapshot.child("description").getValue().toString().trim();
                                                    String ver = childSnapshot.child("verify").getValue().toString();
                                                    String keys = childSnapshot.child("keys").getValue().toString();
                                                    contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                                    contentsz.add(content);
                                                }
                                            }




                                        }
                                        contentsAdaptors.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                                });

                            }
                            else if(selectedItem.equals("Ase")){
                                //Button subscribeButton = findViewById(R.id.Subscribe);
                                //subscribeButton.setText("Verify");
                                postRecyclerView.setAdapter(contentsAdaptors);
                                FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                                databaseReference1 = ref1.getReference().child("Content");
                                contentsz.clear();

                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            if(childSnapshot.child("subscribers").child(username2).exists()){

                                            }
                                            else {

                                                if (childSnapshot.child("subject").getValue().toString().equals("Ase")) {
                                                    String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                                    String id = childSnapshot.child("owner").getValue().toString().trim();
                                                    String price = childSnapshot.child("price").getValue().toString().trim();
                                                    String descr = childSnapshot.child("description").getValue().toString().trim();
                                                    String ver = childSnapshot.child("verify").getValue().toString();
                                                    String keys = childSnapshot.child("keys").getValue().toString();
                                                    contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                                    contentsz.add(content);
                                                }
                                            }




                                        }
                                        contentsAdaptors.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                                });



                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                });
            }
        });












        // Get the list of posts from your data source
        //posts= getPostsFromDataSource();
        //getPostsFromDataSource();
        // Add the fetched posts to the existing list
        //posts.addAll(fetchedPosts);
        //Toast.makeText(Adminhome.this, posts.getName(), Toast.LENGTH_SHORT).show();

        // Notify the adapter about the data change
        //postAdapter.notifyDataSetChanged();

        // Add some example posts
        //Post post1 = new Post("Shashank", "110122650");
        //Post post2 = new Post("Kannan", "11012345");
        //posts.add(post1);
        //posts.add(post2);
        //postAdapter.notifyDataSetChanged();





    }
    private void getPostsFromDataSource() {



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == 1) { // Same request code as used in step 3
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the file download
                downloadedFile(getApplicationContext(), fileUrl, fileName);
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(getApplicationContext(), "Permission denied. Cannot download file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean hasWriteExternalStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static void requestWriteExternalStoragePermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }


    public static void downloadedFile(Context context, String fileUrl, String fileName) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(fileUrl);
        storageRef.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
            String url = downloadUrl.toString();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(fileName)
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }
        }).addOnFailureListener(exception -> {
            Toast.makeText(context, "Failed to retrieve download URL", Toast.LENGTH_SHORT).show();
        });
    }



    private void downloadFile(String idl) {
        // Retrieve the download URL from Firebase Realtime Database
        storageRef = FirebaseStorage.getInstance("gs://wisebridge-c303a.appspot.com").getReference();
        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        databaseRef = ref1.getReference().child("Content").child(idl);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@com.google.firebase.database.annotations.NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String downloadUrl = dataSnapshot.child("url").getValue().toString();
                    if (downloadUrl != null) {
                        // Download the file from Firebase Storage
                        Toast.makeText(Homeuser.this,downloadUrl,Toast.LENGTH_SHORT).show();
                        StorageReference fileRef = FirebaseStorage.getInstance("gs://wisebridge-c303a.appspot.com").getReferenceFromUrl(downloadUrl);
                        try {
                            File localFile = File.createTempFile("downloaded_file", "");
                            fileRef.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(Homeuser.this, "File downloaded!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@com.google.firebase.database.annotations.NotNull Exception e) {
                                            Toast.makeText(Homeuser.this, "Failed to download file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Homeuser.this, "No file found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@com.google.firebase.database.annotations.NotNull DatabaseError databaseError) {
                Toast.makeText(Homeuser.this, "Failed to retrieve file data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
