package com.example.wisebridge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class Reviewpage extends AppCompatActivity {
    public static String RKeys,RDesc,Rido,types;
    TextView Cnamee,Cdesc,Cowner;
    EditText revdes,revrate;
    Button revbut;
    public static RecyclerView ReviewsRecyclerView;
    public static List<Reviews> Reviewsz = new ArrayList<>();
    public static ReviewsAdaptor reviewsAdaptors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewpage);
        RKeys = getIntent().getStringExtra("Keys");
        RDesc = getIntent().getStringExtra("Description");
        Rido = getIntent().getStringExtra("IDowner");
        types = getIntent().getStringExtra("types");
        revbut = findViewById(R.id.revbut);
        revdes = findViewById(R.id.revdes);
        revrate = findViewById(R.id.revrate);
        Toast.makeText(Reviewpage.this, types, Toast.LENGTH_SHORT).show();

        ReviewsRecyclerView = findViewById(R.id.ReviewsRecyclerView21);
        reviewsAdaptors = new ReviewsAdaptor(Reviewsz);
        ReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Toast.makeText(Reviewpage.this, "You pressed: "+RKeys +"\nwith description: " + RDesc + "\nwith ID: "+ Rido, Toast.LENGTH_SHORT).show();
        //Log.d("Tag","You pressed: "+RKeys +"\nwith description: " + RDesc + "\nwith ID: "+ Rido);
        Cnamee =findViewById(R.id.Cnamee);
        Cdesc= findViewById(R.id.Cdesc);
        Cowner=findViewById(R.id.Cowner);

        Cnamee.setText(RKeys);
        Cdesc.setText(RDesc);
        Cowner.setText(Rido);

        if(types.equals("Expert")){
            revrate.setVisibility(View.GONE);
            revdes.setVisibility(View.GONE);
            revbut.setVisibility(View.GONE);
        }
        else{

        }

    }
}
