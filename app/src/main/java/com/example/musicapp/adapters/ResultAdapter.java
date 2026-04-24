package com.example.musicapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.R;
import com.example.musicapp.models.*;

import java.util.ArrayList;

public class ResultAdapter
        extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private ArrayList<Favorite> list;
    private Context context;
    private OnSaveClickListener listener;

    public interface OnSaveClickListener {
        void onSave(Favorite favorite);
    }

    public ResultAdapter(
            ArrayList<Favorite> list,
            Context context,
            OnSaveClickListener listener
    ) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_result,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Favorite favorite = list.get(position);

        holder.textSong.setText(
                favorite.getSongName()
        );

        holder.textArtist.setText(
                favorite.getArtist()
        );

        holder.textAlbum.setText(
                favorite.getAlbum()
        );

        // Cargar imagen con Glide
        Glide.with(context)
                .load(favorite.getImageUrl())
                .placeholder(R.drawable.ic_music)
                .error(R.drawable.ic_music)
                .into(holder.imageSong);

        holder.buttonSave.setOnClickListener(v -> {

            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                Favorite fav = list.get(pos);

                if (listener != null) {
                    listener.onSave(fav);
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView textSong;
        TextView textArtist;
        TextView textAlbum;
        ImageView imageSong;
        Button buttonSave;

        public ViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            textSong =
                    itemView.findViewById(
                            R.id.textSong
                    );

            textArtist =
                    itemView.findViewById(
                            R.id.textArtist
                    );

            textAlbum =
                    itemView.findViewById(
                            R.id.textAlbum
                    );

            imageSong =
                    itemView.findViewById(
                            R.id.imageSong
                    );

            buttonSave =
                    itemView.findViewById(
                            R.id.buttonSave
                    );
        }
    }
}