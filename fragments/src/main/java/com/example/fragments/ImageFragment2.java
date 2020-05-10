package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_image2, container, false);
    }

    public void changeIcon() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int iconId = bundle.getInt("ImageId");
            ImageView imageView = getView().findViewById(R.id.img);
            imageView.setImageResource(iconId);
        }
    }
}
