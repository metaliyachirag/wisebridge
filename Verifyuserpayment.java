package com.example.wisebridge;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class Verifyuserpayment extends AppCompatActivity {
    public static RecyclerView postRecyclerView;
    public static PostAdapter postAdapter1;
    Button logout;
    Spinner userTypeSpinnerpay;
    public static List<Post> posts1 = new ArrayList<>();
    public static String userins;
    DatabaseReference databaseReferencepay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyuserpayment);
        postRecyclerView = findViewById(R.id.postRecyclerViewpay);
        userTypeSpinnerpay = findViewById(R.id.userTypeSpinnerpay);
        logout = findViewById(R.id.logoutpayv);
        postAdapter1 = new PostAdapter(posts1,"Subscribers");
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verifyuserpayment.this, Login.class);
                startActivity(intent);
            }
        });
        FirebaseDatabase refer = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");

        userTypeSpinnerpay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posts1.clear();
                postAdapter1.notifyDataSetChanged();
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Subscribers")){
                    //Toast.makeText(Verifyuserpayment.this,"Subscribers list", Toast.LENGTH_SHORT).show();
                    String keypay = getIntent().getStringExtra("keyss");

                    userins="Subscribers";
                    postAdapter1 = new PostAdapter(posts1,userins);
                    postRecyclerView.setAdapter(postAdapter1);
                   FirebaseDatabase refer = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");

                   databaseReferencepay = refer.getReference().child("Content").child(keypay).child("subscribers");


                   databaseReferencepay.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot snapshot1) {
                          // Toast.makeText(Verifyuserpayment.this, snapshot1.hasChildren(), Toast.LENGTH_SHORT).show();
                           //Log.d("TAG",snapshot.getChildren().toString());
                           if(snapshot1.hasChildren()){
                               //Toast.makeText(Verifyuserpayment.this,"hello", Toast.LENGTH_SHORT).show();
                               for (DataSnapshot childSnapshot : snapshot1.getChildren()) {
                                   if(childSnapshot.child("payverify").exists()){
                                       String name = childSnapshot.getKey().toString().trim();
                                       String verid = childSnapshot.child("verification_id").getValue().toString();
                                       String ver = childSnapshot.child("payverify").getValue().toString();

                                       if (ver.equals("1")) {
                                           Post updatedPost = new Post(name, verid);
                                           posts1.add(updatedPost);
                                       }
                                   }
                                   else{
                                       String name = childSnapshot.getKey().toString().trim();
                                       String keypay = getIntent().getStringExtra("keyss");
                                       String verid = keypay ;
                                       //String ver = childSnapshot.child("payverify").getValue().toString();
                                       Post updatedPost = new Post(name, verid);
                                       posts1.add(updatedPost);

                                   }



                               }
                               postAdapter1.notifyDataSetChanged();
                           }
                           else{
                              // Toast.makeText(Verifyuserpayment.this,"No Subscribers", Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError error) {

                       }
                   });


                }
                else if(selectedItem.equals("Verify payment")){
                    //Toast.makeText(Verifyuserpayment.this,"Verify payment of users", Toast.LENGTH_SHORT).show();
                    String keypay = getIntent().getStringExtra("keyss");

                    userins="Verify payment";
                    postAdapter1 = new PostAdapter(posts1,userins);
                    postRecyclerView.setAdapter(postAdapter1);
                    FirebaseDatabase refer = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");

                    databaseReferencepay = refer.getReference().child("Content").child(keypay).child("subscribers");

                    databaseReferencepay.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                //Toast.makeText(Verifyuserpayment.this,keypay, Toast.LENGTH_SHORT).show();
                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                    if(childSnapshot.child("payverify").exists()){
                                        String name = childSnapshot.getKey().toString().trim();
                                        String verid = childSnapshot.child("verification_id").getValue().toString();
                                        String ver = childSnapshot.child("payverify").getValue().toString();

                                        if (ver.equals("0")) {
                                            Post updatedPost = new Post(name, verid);
                                            posts1.add(updatedPost);
                                        }
                                    }
                                    else{

                                    }


                                }
                                postAdapter1.notifyDataSetChanged();
                            }
                            else{
                               // Toast.makeText(Verifyuserpayment.this,"No Subscribers", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                    postAdapter1.setOnVerssButtonClickListener(new PostAdapter.OnVerssButtonClickListener() {
                        @Override
                        public void onVerssButtonClick(Post post) {
                          //  Toast.makeText(Verifyuserpayment.this,"Hello"+post.getName(),Toast.LENGTH_SHORT).show();

                            String keypay = getIntent().getStringExtra("keyss");
                            FirebaseDatabase refer = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");

                            databaseReferencepay = refer.getReference().child("Content").child(keypay).child("subscribers");
                            //Toast.makeText(Verifyuserpayment.this,"Hello"+post.getName(),Toast.LENGTH_SHORT).show();
                            databaseReferencepay.child(post.getName()).child("payverify").setValue("1");
                            postAdapter1.removePost(post);
                            // ...
                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}
