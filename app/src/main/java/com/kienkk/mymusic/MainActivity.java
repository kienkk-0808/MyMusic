package com.kienkk.mymusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;

import com.kienkk.mymusic.Model.Album;
import com.kienkk.mymusic.Model.Song;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        songArrayList = findSong();
        Log.d("Size",songArrayList.size() + "  ");
    }
    private void checkPermission(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
    }

    private ArrayList<Song> findSong(){
        ArrayList<Song> listSong = new ArrayList<>();
        ArrayList<Album> albumArrayList = new ArrayList<>();
        Uri uriAudio = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri uriAlbum = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projectionAudio = {MediaStore.Audio.AudioColumns._ID,
                                MediaStore.Audio.AudioColumns.TITLE,
                                MediaStore.Audio.AudioColumns.ARTIST,
                                MediaStore.Audio.AudioColumns.DATA,
                                MediaStore.Audio.AudioColumns.DURATION,
                                MediaStore.Audio.AudioColumns.ALBUM,
                                MediaStore.Audio.AudioColumns.ALBUM_KEY,
                                MediaStore.Audio.AudioColumns.ALBUM_ID};

        String[] projectionAlbum = {
                MediaStore.Audio.AlbumColumns.ALBUM_KEY,
                MediaStore.Audio.AlbumColumns.ALBUM_ART
        };

        Cursor album = getContentResolver().query(uriAlbum, projectionAlbum,null,null,null);
        Cursor c = getContentResolver().query(uriAudio, projectionAudio,null,null,null);

        if (album != null) {
            while (album.moveToNext()) {
                Album a = new Album();
                a.setAlbumKey(album.getString(0));
                Bitmap bitmap = BitmapFactory.decodeFile(album.getString(1));
                a.setAlbumArt(bitmap);
                albumArrayList.add(a);
            }
            album.close();
        }

        if (c != null) {
            while (c.moveToNext()) {
                Song song = new Song();
                song.setSongId(c.getLong(0));
                song.setSongTitle(c.getString(1));
                song.setSongArtist(c.getString(2));
                song.setPath(c.getString(3));
                song.setDuration(c.getLong(4));
                song.setAlbum(c.getString(5));
                Bitmap bitmap = null;
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, c.getLong(7));
                        bitmap = getContentResolver().loadThumbnail(contentUri, new Size(300, 300), null);
                        song.setAlbumArt(bitmap);
                    }else {
                        for (int i = 0; i < albumArrayList.size(); i++){
                            if (c.getString(6).equals(albumArrayList.get(i).getAlbumKey())){
                                song.setAlbumArt(albumArrayList.get(i).getAlbumArt());
                            }
                        }
                    }
                }catch (Exception e){
                    for (int i = 0; i < albumArrayList.size(); i++){
                        if (c.getString(6).equals(albumArrayList.get(i).getAlbumKey())){
                            song.setAlbumArt(albumArrayList.get(i).getAlbumArt());
                        }
                    }
                    Log.d("Load", e.toString());
                }
                if (song.getDuration() > 10){
                    listSong.add(song);
                }
            }
            c.close();
        }

        return listSong;
    }
}