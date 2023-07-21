package com.example.wisebridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
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

import org.w3c.dom.Text;

public class payment extends AppCompatActivity {
    public static String selectedItem,unames,acco,ifs;
    public static DatabaseReference databaseReferencepay,databaseReferencepay1 ;
    private StorageReference storageRef;
    EditText payusername1;
    Button payverbtn;
    TextView transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String un = getIntent().getStringExtra("username3");
        String key = getIntent().getStringExtra("keys3");

        payusername1 = findViewById(R.id.payusername);
        payverbtn = findViewById(R.id.payverbtn);
        transfer = findViewById(R.id.transfer);
        //String l = un+" should pay to download this content "+ key;
        //Toast.makeText(payment.this, un+" should pay to download this content "+ key, Toast.LENGTH_SHORT).show();

       //payusername1.setText(l);
        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        databaseReferencepay = ref1.getReference().child("Content").child(key);
        databaseReferencepay.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String exp = snapshot.child("owner").getValue().toString().trim();
                databaseReferencepay1 = ref1.getReference().child("ExpRegister").child(exp);
                databaseReferencepay1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot1) {
                        String pr = getIntent().getStringExtra("pr");
                        acco = snapshot1.child("account").getValue().toString().trim();
                        ifs = snapshot1.child("ifsc").getValue().toString().trim();
                        String xd = "Transfer CAD "+ pr +" to "+exp+":"+ "\nAccount: "+acco+"\nIfsc: "+ifs;
                        transfer.setText(xd);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });



       
}
