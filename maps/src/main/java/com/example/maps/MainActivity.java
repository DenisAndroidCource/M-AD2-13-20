package com.example.maps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maps.map.MapFragment;
import com.example.maps.photoviewer.PhotoViewerFragment;

public class MainActivity extends AppCompatActivity implements MapFragment.MapFragmentActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openMapFragment();
    }

    private void openMapFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, MapFragment.getInstance(), MapFragment.TAG)
                .commit();
    }

    private void openPhotoViewerFragment(long photoId) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, PhotoViewerFragment.newInstance(photoId), PhotoViewerFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPhotoClickAction(long photoId) {
        openPhotoViewerFragment(photoId);
    }
}
