package com.kienkk.mymusic.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Song {
    private long songId;
    private String songTitle;
    private String songArtist;
    private String path;
    private long duration;
    private String album;
    private Bitmap albumArt;

    public Song() {}

    public Song(long songId, String songTitle, String songArtist, String path, short genre, long duration, String album) {
        this.songId = songId;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.path = path;
        this.duration = duration;
        this.album = album;
        this.albumArt = albumArt;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Bitmap getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Bitmap albumArt) {
        this.albumArt = albumArt;
    }
}

