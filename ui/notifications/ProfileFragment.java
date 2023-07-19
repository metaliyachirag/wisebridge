package com.example.wisebridge.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wisebridge.Login;
import com.example.wisebridge.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    public static DatabaseReference userRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView un = binding.un;
        final TextView uid = binding.uid;
        final TextView umail = binding.umail;
        final Button signOutButton = binding.signOutButton;

        String username = requireActivity().getIntent().getStringExtra("username");
        String usertype = requireActivity().getIntent().getStringExtra("type");

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        // Use the username to access corresponding children in the Firebase database
        if(usertype.equals("Student")){
            userRef = FirebaseDatabase.getInstance().getReference().child("Register").child(username);
        }
        else if(usertype.equals("Expert")){
            userRef = FirebaseDatabase.getInstance().getReference().child("ExpRegister").child(username);
        }

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Access the children and display the data as needed
                    String name = dataSnapshot.child("name").getValue(String.class).trim();
                    String email = dataSnapshot.child("email").getValue(String.class).trim();

                    // Use the retrieved data as desired
                    un.setText(name);
                    uid.setText(username);
                    umail.setText(email);

                } else {
                    // Handle the case when the user does not exist in the database
                    Log.d("Profile", "User does not exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Profile", "Error getting data: " + databaseError.getMessage());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}