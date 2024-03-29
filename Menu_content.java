package com.example.wisebridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
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

public class Menu_content extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference1;
    Button logout;
    Spinner userTypeSpinnerad;
    public static RecyclerView postRecyclerView;
    public static PostAdapter postAdapter;
    public static contentsAdaptor contentsAdaptors;
    public static List<Post> posts = new ArrayList<>();
    public static List<contents> contentsz = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        logout = findViewById(R.id.logoutadmin);
        userTypeSpinnerad = findViewById(R.id.userTypeSpinnerad);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Adminhome.this, Login.class);
                startActivity(intent);
            }
        });


        postRecyclerView = findViewById(R.id.postRecyclerView);
        postAdapter = new PostAdapter(posts);
        contentsAdaptors = new contentsAdaptor(contentsz);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userTypeSpinnerad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posts.clear();
                contentsz.clear();
                postAdapter.notifyDataSetChanged();
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(Adminhome.this, "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();
                if(selectedItem.equals("Content")){
                    //Button subscribeButton = findViewById(R.id.Subscribe);
                    //subscribeButton.setText("Verify");
                    postRecyclerView.setAdapter(contentsAdaptors);
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Content");
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                String id = childSnapshot.child("owner").getValue().toString().trim();
                                String price = childSnapshot.child("price").getValue().toString().trim();
                                String descr = childSnapshot.child("description").getValue().toString().trim();
                                String ver = childSnapshot.child("verify").getValue().toString();
                                String keys = childSnapshot.child("keys").getValue().toString();
                                if(ver.equals("0")){
                                    contents content = new contents(name, id,descr,price,keys); // Create a new Post object

                                    contentsz.add(content);
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
                Toast.makeText(getApplicationContext(), keys, Toast.LENGTH_SHORT).show();
                FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                databaseReference1 = ref1.getReference().child("Content");
                databaseReference1.child(keys).child("verify").setValue("1");
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        contentsz.clear();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String name = childSnapshot.child("title").getValue(String.class); // Get the name
                            String id = childSnapshot.child("owner").getValue().toString().trim();
                            String price = childSnapshot.child("price").getValue().toString().trim();
                            String descr = childSnapshot.child("description").getValue().toString().trim();
                            String ver = childSnapshot.child("verify").getValue().toString();
                            String keys = childSnapshot.child("keys").getValue().toString();
                            if(ver.equals("0")){
                                contents content = new contents(name, id,descr,price,keys); // Create a new Post object

                                contentsz.add(content);
                            }

                        }
                        contentsAdaptors.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                });
            }
        });



        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                Toast.makeText(Adminhome.this, "Verified: " + post.getName() + " : "+ post.getId(), Toast.LENGTH_SHORT).show();
                databaseReference.child(post.getId()).child("verify").setValue("1");

                // Refresh the page (RecyclerView) by clearing and re-fetching the data
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        posts.clear(); // Clear the list before re-fetching the data

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String name = childSnapshot.child("name").getValue(String.class);
                            String id = childSnapshot.getKey();
                            String ver = childSnapshot.child("verify").getValue().toString();

                            if (ver.equals("0")) {
                                Post updatedPost = new Post(name, id);
                                posts.add(updatedPost);
                            }
                        }

                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                        // Handle the error if any
                    }
                });
            }
        });



    }
    private void getPostsFromDataSource() {



    }

}
