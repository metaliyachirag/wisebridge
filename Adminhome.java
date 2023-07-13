package com.example.wisebridge;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Adminhome extends AppCompatActivity {
    DatabaseReference databaseReference;

    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private List<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);



        postRecyclerView = findViewById(R.id.postRecyclerView);
        postAdapter = new PostAdapter(posts);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerView.setAdapter(postAdapter);


        // Get the list of posts from your data source
        //List<Post> fetchedPosts = getPostsFromDataSource();

        // Add the fetched posts to the existing list
        //posts.addAll(fetchedPosts);

        // Notify the adapter about the data change
        postAdapter.notifyDataSetChanged();

        Post post1 = new Post("student 1", "11012456");
        Post post2 = new Post("student 2", "11012345");
        posts.add(post1);
        posts.add(post2);
        postAdapter.notifyDataSetChanged();




    }
    private List<Post> getPostsFromDataSource() {
        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        databaseReference = ref1.getReference().child("Register");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle the error if any
            }
        });
        
    }

}
