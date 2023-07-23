package com.example.wisebridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class forgotpassword extends AppCompatActivity {
    EditText oldpass,newpass,ussernames;
    Spinner userspinner;
    DatabaseReference databaseReference1;
    Button chgpass;
    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        ussernames = findViewById(R.id.usepass);
        userspinner = findViewById(R.id.userTypeSpinnerpass);
        chgpass = findViewById(R.id.chgpass);

        chgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = ussernames.getText().toString().trim();
                String opass = oldpass.getText().toString().trim();
                String eopass = encrypt(opass);
                String npass = newpass.getText().toString().trim();
                String enpass = encrypt(npass);
                String cls = userspinner.getSelectedItem().toString();
                if (!uid.isEmpty() && !opass.isEmpty() && !npass.isEmpty()){
                    if (cls.equals("Student")) {
                        //Toast.makeText(forgotpassword.this, "Welcome student", Toast.LENGTH_SHORT).show();
                        databaseReference1 = ref1.getReference().child("Register").child(uid);
                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    newpass.setText(snapshot.getKey().toString());
                                    if (snapshot.child("password").getValue().toString().trim().equals(eopass)) {
                                        databaseReference1.child("password").setValue(enpass);
                                        Toast.makeText(forgotpassword.this, "Successfully changed password", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(forgotpassword.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(forgotpassword.this, "You have entered incorrect old password", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(forgotpassword.this, "The student user does not exist, please enter a valid student user id", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });

                    } else if (cls.equals("Expert")) {
                        databaseReference1 = ref1.getReference().child("ExpRegister").child(uid);

                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    if (snapshot.child("password").getValue().toString().trim().equals(eopass)) {
                                        databaseReference1.child("password").setValue(enpass);
                                        Toast.makeText(forgotpassword.this, "Successfully changed password", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(forgotpassword.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(forgotpassword.this, "You have entered incorrect old password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    Toast.makeText(forgotpassword.this, "The Expert user does not exist, please enter a valid Expert user id", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                    }
            }
                else{
                    Toast.makeText(forgotpassword.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String encrypt(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                char base = Character.isLowerCase(chars[i]) ? 'a' : 'A';
                chars[i] = (char) (base + (chars[i] - base + 1) % 26);
            }
        }
        return new String(chars);
    }
}
