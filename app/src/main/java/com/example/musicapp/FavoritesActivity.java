package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.musicapp.models.Favorite;
import com.example.musicapp.adapters.FavoriteAdapter;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button buttonLogout;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private ArrayList<Favorite> favoriteList;
    private ArrayList<Favorite> filteredList;
    private FavoriteAdapter adapter;
    private EditText editTextSearch;
    private Button buttonSearch;
    private Button buttonBackToSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recyclerViewFavorites);
        buttonLogout = findViewById(R.id.buttonLogout);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);

        favoriteList = new ArrayList<>();
        filteredList = new ArrayList<>();

        buttonBackToSearch =
                findViewById(R.id.buttonBackToSearch);

        buttonBackToSearch.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            FavoritesActivity.this,
                            SearchActivity.class
                    )
            );

            finish();

        });

        buttonSearch.setOnClickListener(
                v -> searchFavorites()
        );

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavoriteAdapter(
                filteredList,
                this,
                favoriteId -> deleteFavorite(favoriteId)
        );

        recyclerView.setAdapter(adapter);

        buttonLogout.setOnClickListener(v -> logoutUser());

        loadFavorites();
    }

    // SOLO FIREBASE (NO API)
    private void loadFavorites() {

        FirebaseUser user = auth.getCurrentUser();

        if (user == null) return;

        String userId = user.getUid();

        db.collection("favorites")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    favoriteList.clear();
                    filteredList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        Favorite favorite = doc.toObject(Favorite.class);
                        favorite.setId(doc.getId());

                        favoriteList.add(favorite);
                        filteredList.add(favorite);
                    }

                    adapter.notifyDataSetChanged();

                })
                .addOnFailureListener(e -> {
                    Log.e("FIREBASE", e.toString());
                    Toast.makeText(this, "Error cargando favoritos", Toast.LENGTH_SHORT).show();
                });
    }
    private void searchFavorites() {

        String query =
                editTextSearch
                        .getText()
                        .toString()
                        .toLowerCase()
                        .trim();

        filteredList.clear();

        if (query.isEmpty()) {

            filteredList.addAll(favoriteList);

        } else {

            for (Favorite favorite : favoriteList) {

                if (
                        favorite.getSongName()
                                .toLowerCase()
                                .contains(query)
                                ||
                                favorite.getArtist()
                                        .toLowerCase()
                                        .contains(query)
                                ||
                                favorite.getAlbum()
                                        .toLowerCase()
                                        .contains(query)
                ) {

                    filteredList.add(favorite);

                }
            }
        }

        adapter.notifyDataSetChanged();
    }
    // 🗑️ DELETE
    public void deleteFavorite(String documentId) {

        db.collection("favorites")
                .document(documentId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                    loadFavorites();
                })
                .addOnFailureListener(e -> {
                    Log.e("DELETE", e.toString());
                });
    }

    // logout
    private void logoutUser() {

        auth.signOut();

        startActivity(new Intent(
                FavoritesActivity.this,
                LoginActivity.class
        ));

        finish();
    }
}