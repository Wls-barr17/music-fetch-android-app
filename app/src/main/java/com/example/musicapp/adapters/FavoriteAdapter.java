package com.example.musicapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.R;
import com.example.musicapp.models.Favorite;
import com.example.musicapp.FavoritesActivity;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<Favorite> favoriteList;
    private Context context;
    private OnDeleteClickListener listener;

    public interface OnDeleteClickListener {
        void onDelete(String favoriteId);
    }

    public FavoriteAdapter(
            ArrayList<Favorite> favoriteList,
            Context context,
            OnDeleteClickListener listener
    ) {
        this.favoriteList = favoriteList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.item_favorite,
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

        Favorite favorite =
                favoriteList.get(position);

        holder.textSong.setText(
                favorite.getSongName()
        );

        holder.textArtist.setText(
                favorite.getArtist()
        );

        holder.textAlbum.setText(
                favorite.getAlbum()
        );

        holder.buttonDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Eliminar")
                    .setMessage(
                            "¿Eliminar esta favorita?"
                    )
                    .setPositiveButton(
                            "Sí",
                            (dialog, which) -> {

                                if (listener != null) {

                                    listener.onDelete(
                                            favorite.getId()
                                    );

                                }

                            }
                    )
                    .setNegativeButton(
                            "No",
                            null
                    )
                    .show();

        });

    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView textSong;
        TextView textArtist;
        TextView textAlbum;

        Button buttonDelete;

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

            buttonDelete =
                    itemView.findViewById(
                            R.id.buttonDelete
                    );

        }
    }
}