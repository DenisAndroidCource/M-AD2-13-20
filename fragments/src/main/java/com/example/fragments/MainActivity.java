package com.example.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fragments.imgtest.ImageFragment1;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnImageChangeListener{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction()
                .add(R.id.fragmentImageContainer, new ImageFragment())
                .addToBackStack("Transaction1")
                .commit();


        Bundle bundle = new Bundle();
        bundle.putInt("ImageId", R.drawable.ic_android_black_24dp);
        ImageFragment2 imageFragment2 = new ImageFragment2();
        imageFragment2.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.fragmentImageContainer1, imageFragment2, "ImageFragment2")
                .addToBackStack("Transaction1")
                .commit();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentManager.popBackStack(null, 0);

                Fragment fragment = fragmentManager.findFragmentByTag("TAG");
                Fragment fragment2 = fragmentManager.findFragmentById(R.id.fragmentImageContainer);

                List<Fragment> list = fragmentManager.getFragments();

                fragmentManager.popBackStack(null, 0);
            }
        });
    }

    @Override
    public void changeImage() {
        Fragment fragment = fragmentManager.findFragmentByTag("ImageFragment2");
        if (fragment != null && fragment instanceof ImageFragment2) {
            ((ImageFragment2)fragment).changeIcon();
        }
    }


//    "Transaction1" -> "Transaction2" -> "Transaction3" -> "Transaction4" -> "Transaction5"
}
