package com.example.wisebridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class contentsAdaptor extends RecyclerView.Adapter<contentsAdaptor.contentsViewHolder> {
    private List<contents> contentsz;
    private OnItemClickListener clickListener;
    public static Button subscribeButton, downbutton;
    private String userInfo;

    public contentsAdaptor(List<contents> contentsz, String userInfo) {
        this.contentsz = contentsz; this.userInfo = userInfo;
    }

    public interface OnDownButtonClickListener {
        void onDownButtonClick(int position);
    }
    private OnDownButtonClickListener downButtonClickListener;

    public void setOnDownButtonClickListener(OnDownButtonClickListener listener) {
        this.downButtonClickListener = listener;
    }

    // Click listener interface
    public interface OnItemClickListener {
        void onItemClick(contents content);
    }

    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }




    @NotNull
    @Override
    public contentsViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contents_item_layout, parent, false);
        return new contentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull contentsViewHolder holder, int position) {
        contents content = contentsz.get(position);
        holder.bind(content);
    }

    @Override
    public int getItemCount() {
        return contentsz.size();
    }

    public class contentsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleName;
        private TextView ids;
        private TextView descript;
        private TextView price;

        public contentsViewHolder(@NotNull View itemView) {
            super(itemView);
            titleName = itemView.findViewById(R.id.title);
            ids = itemView.findViewById(R.id.ids);
            descript = itemView.findViewById(R.id.descriptionTextView);
            price = itemView.findViewById(R.id.price);
            subscribeButton = itemView.findViewById(R.id.Subscribe);
            downbutton = itemView.findViewById(R.id.downbutton);


            if(userInfo.equals("user")){
                subscribeButton.setText("Subscribe");
                downbutton.setText("View more");
            }else if(userInfo.equals("admin")){
                subscribeButton.setText("Verify");
                downbutton.setVisibility(View.VISIBLE);
            }
            else if(userInfo.equals("usersub")){
                subscribeButton.setText("View information");
                downbutton.setText("Download");
            }
            else if(userInfo.equals("expert")){
                subscribeButton.setText("View information");
                downbutton.setText("View subscribers");
            }

            // Set click listener for the button
            Button button = itemView.findViewById(R.id.Subscribe);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && clickListener != null) {
                        clickListener.onItemClick(contentsz.get(position));
                    }
                }
            });
            downbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && downButtonClickListener != null) {
                        downButtonClickListener.onDownButtonClick(position);
                    }
                }
            });

            // Set click listener for the item view

        }


        public void bind(contents content) {
            titleName.setText(content.getName());
            ids.setText(content.getId());
            descript.setText(content.getDescript());
            price.setText(content.getPrice());

        }

    }
}

