package com.example.maps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.maps.map.MapFragment;
import com.example.maps.photoviewer.PhotoViewerFragment;

public class MainActivity extends AppCompatActivity implements MapFragment.MapFragmentActionListener {

    public static final String EXTRA_OPEN_PHOTO_ID = "EXTRA_OPEN_PHOTO_ID";
    public static final String EXTRA_OPEN_PHOTO_ACTION = "com.example.maps.OPEN_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openMapFragment();
        onNewIntentAction(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onNewIntentAction(intent);
    }

    private void openMapFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, MapFragment.getInstance(), MapFragment.TAG)
                .commit();
    }

    private void openPhotoViewerFragment(long photoId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PhotoViewerFragment fragment = (PhotoViewerFragment) fragmentManager.findFragmentByTag(PhotoViewerFragment.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, PhotoViewerFragment.newInstance(photoId), PhotoViewerFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPhotoClickAction(long photoId) {
        openPhotoViewerFragment(photoId);
    }

    private void onNewIntentAction(@Nullable Intent intent) {
        if (intent != null &&  intent.getAction() != null && hasOpenPhotoIntent(intent)) {
            openPhotoViewerFragment(intent.getLongExtra(EXTRA_OPEN_PHOTO_ID, 0));
        }
    }

    private boolean hasOpenPhotoIntent(Intent intent){
        return intent.getAction().equals(EXTRA_OPEN_PHOTO_ACTION) && intent.hasExtra(EXTRA_OPEN_PHOTO_ID);
    }
}
