package com.example.wisebridge;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Backend_Login extends AppCompatActivity {
    Button loginbtn;
    EditText username, password;
    DatabaseReference databaseReference;
    TextView Registerpage;
    Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FirebaseApp.initializeApp(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        loginbtn = (Button)findViewById(R.id.Registerbtn);
        username = (EditText)findViewById(R.id.Rusername);
        password = (EditText)findViewById(R.id.Rpassword);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);


        Registerpage = findViewById(R.id.signup);
        Registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Registration activity
                Intent intent = new Intent(Login.this, Registeration.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String userType = userTypeSpinner.getSelectedItem().toString();

                if(!uname.isEmpty() && !pass.isEmpty() && !userType.isEmpty()){
                    if(userType.equals("Admin")){
                        databaseReference = ref1.getReference().child("Admin").child(uname);
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    String pascheck = snapshot.child("password").getValue().toString().trim();
                                    if(pascheck.equals(pass)){
                                        Toast.makeText(Login.this, "You can login", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, Adminhome.class);
                                        intent.putExtra("username", uname);
                                        intent.putExtra("type",userType);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Login.this, "The password you entered is incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(Login.this, "The admin does not exist, please try with a valid admin id", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.e("Login", "Error getting data: " + error.getMessage());
                            }
                        });

                    }
                   

                                }else{
                                    Toast.makeText(Login.this, "The student user id does not exist, please try with a valid user id", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.e("Login", "Error getting data: " + error.getMessage());
                            }
                        });
                    }

                } else {
                    // EditText fields are empty
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }
}
