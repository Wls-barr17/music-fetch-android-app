package com.example.musicapp.models;

public class Favorite {

    private String id;
    private String songName;
    private String artist;
    private String album;
    private String imageUrl;
    private String userId;
    private Long createdAt;

    public Favorite() {
        // Constructor vacío requerido por Firestore
    }

    public Favorite(
            String songName,
            String artist,
            String album,
            String imageUrl,
            String userId,
            Long createdAt
    ) {
        this.songName = songName;
        this.artist = artist;
        this.album = album;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // ---------- GETTERS Y SETTERS ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}