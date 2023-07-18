package com.example.wisebridge.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wisebridge.databinding.FragmentContentBinding;

public class ContentFragment extends Fragment {

    private FragmentContentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String username = requireActivity().getIntent().getStringExtra("username");
        String usertype = requireActivity().getIntent().getStringExtra("type");

        TextView tv = binding.textDashboard;
        tv.setText(username);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}