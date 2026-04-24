package com.example.musicapp;

import android.content.Intent;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.musicapp.adapters.ResultAdapter;
import com.example.musicapp.models.Favorite;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private Button buttonFavorites;

    private RecyclerView recyclerView;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private ArrayList<Favorite> resultsList;
    private ResultAdapter adapter;

    private static final String API_KEY =
            "your_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearch =
                findViewById(R.id.editTextSearch);

        buttonSearch =
                findViewById(R.id.buttonSearch);

        buttonFavorites =
                findViewById(R.id.buttonFavorites);

        recyclerView =
                findViewById(R.id.recyclerViewResults);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        resultsList = new ArrayList<>();

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter =
                new ResultAdapter(
                        resultsList,
                        this,
                        this::saveFavorite
                );

        recyclerView.setAdapter(adapter);

        buttonSearch.setOnClickListener(
                v -> searchSong()
        );

        buttonFavorites.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            SearchActivity.this,
                            FavoritesActivity.class
                    )
            );

        });

    }

    private void searchSong() {

        String query =
                editTextSearch
                        .getText()
                        .toString()
                        .trim();

        if (query.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese una canción",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String url =
                "https://ws.audioscrobbler.com/2.0/"
                        + "?method=track.search"
                        + "&track=" + Uri.encode(query)
                        + "&api_key=" + API_KEY
                        + "&format=json";

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,

                        response -> {
                            Log.d("API_RESPONSE", response.toString());

                            try {

                                JSONObject results =
                                        response.getJSONObject("results");

                                JSONObject trackMatches =
                                        results.getJSONObject("trackmatches");

                                JSONArray tracks =
                                        trackMatches.getJSONArray("track");

                                resultsList.clear();

                                for (int i = 0; i < tracks.length(); i++) {

                                    JSONObject track =
                                            tracks.getJSONObject(i);

                                    String name =
                                            track.optString("name");

                                    String artist =
                                            track.optString("artist");

                                    // 1) Obtener imagen

                                    JSONArray images =
                                            track.getJSONArray("image");

                                    String imageUrl = "";

                                    if (images.length() > 0) {

                                        JSONObject imageObject =
                                                images.getJSONObject(
                                                        images.length() - 1
                                                );

                                        imageUrl =
                                                imageObject.optString(
                                                        "#text"
                                                );
                                    }

                                    // 2) Obtener usuario

                                    FirebaseUser user =
                                            auth.getCurrentUser();

                                    // 3) Crear objeto con la imagen

                                    Favorite favorite =
                                            new Favorite(
                                                    name,
                                                    artist,
                                                    "Unknown",
                                                    imageUrl,
                                                    user != null ? user.getUid() : "",
                                                    System.currentTimeMillis()
                                            );

                                    resultsList.add(favorite);
                                }

                                adapter.notifyDataSetChanged();

                            } catch (Exception e) {

                                Log.e(
                                        "PARSE_ERROR",
                                        e.toString()
                                );

                                Toast.makeText(
                                        this,
                                        "Error procesando respuesta",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                        },

                        error -> {

                            Log.e(
                                    "API_ERROR",
                                    error.toString()
                            );

                            Toast.makeText(
                                    this,
                                    "Error de red",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                );

        Volley
                .newRequestQueue(this)
                .add(request);
    }

    private void saveFavorite(Favorite favorite) {

        FirebaseUser user = auth.getCurrentUser();

        if (user == null) return;

        Map<String, Object> data =
                new HashMap<>();

        data.put(
                "userId",
                user.getUid()
        );

        data.put(
                "songName",
                favorite.getSongName()
        );

        data.put(
                "artist",
                favorite.getArtist()
        );

        data.put(
                "album",
                favorite.getAlbum()
        );

        data.put(
                "imageUrl",
                favorite.getImageUrl()
        );

        data.put(
                "createdAt",
                System.currentTimeMillis()
        );

        db.collection("favorites")
                .add(data)
                .addOnSuccessListener(unused ->

                        Toast.makeText(
                                this,
                                "Guardado en favoritos",
                                Toast.LENGTH_SHORT
                        ).show()
                )
                .addOnFailureListener(e ->

                        Log.e(
                                "SAVE_ERROR",
                                e.toString()
                        )
                );
    }
}