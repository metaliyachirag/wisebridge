package com.example.wisebridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> posts;
    private OnItemClickListener clickListener;
    Button verss;
    String userin;

    public void removePost(Post post) {
        int position = posts.indexOf(post);
        if (position != -1) {
            posts.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Click listener interface for verss button
    public interface OnVerssButtonClickListener {
        void onVerssButtonClick(Post post);
    }

    // Listener instance for the click events
    private OnVerssButtonClickListener verssButtonClickListener;

    // Method to set the click listener for the verss button
    public void setOnVerssButtonClickListener(OnVerssButtonClickListener listener) {
        this.verssButtonClickListener = listener;
    }

    public PostAdapter(List<Post> posts, String userin) {
        this.posts = posts; this.userin=userin;
    }
    // Click listener interface
    public interface OnItemClickListener {
        void onItemClick(Post post);
    }
    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }


    @NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post,clickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        private TextView id;

        public PostViewHolder(@NotNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.titleTextView);
            id = itemView.findViewById(R.id.descriptionTextView);
            verss = itemView.findViewById(R.id.verss);

            if(userin.equals("Student") || userin.equals("Expert")){
                verss.setVisibility(View.GONE);
            }
            if(userin.equals("Verify payment")){
                verss.setText("Verify User payment");
            }
            if(userin.equals("Subscribers")){
                verss.setVisibility(View.GONE);
            }
            if(userin.equals("All Users")){
                verss.setVisibility(View.GONE);
            }

            verss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && verssButtonClickListener != null) {
                        Post post = posts.get(position);
                        verssButtonClickListener.onVerssButtonClick(post);
                    }
                }
            });

            // Set click listener for the item view

        }



        public void bind(Post post, OnItemClickListener listener) {
            Name.setText(post.getName());
            id.setText(post.getId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(post);
                    }
                }
            });
        }
    }
}

