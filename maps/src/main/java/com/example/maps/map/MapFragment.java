package com.example.maps.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.maps.R;
import com.example.maps.utils.CameraUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MapFragment extends Fragment {

    public interface MapFragmentActionListener {
        void onPhotoClickAction(long photoId);
    }

    public static final String TAG = "MapFragment";
    private static final int LOCATION_REQUEST_CODE = 18000;
    private static final int CAMERA_REQUEST_CODE = 18001;
    private static final int TAKE_PICTURE_REQUEST_CODE = 18002;

    public static MapFragment getInstance() {
        return new MapFragment();
    }

    private Optional<GoogleMap> googleMapOptional = Optional.empty();
    private MapFragmentViewModel mapFragmentViewModel;
    private File tempFile;
    private MapFragmentActionListener mapFragmentActionListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MapFragmentActionListener) {
            mapFragmentActionListener = (MapFragmentActionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewModel();
        initMap();
    }

    @OnClick(R.id.buttonOpenCamera)
    void onOpenCamera() {
        checkCameraPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            mapFragmentViewModel.savePhoto(tempFile);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                enableLocation();
            }
        } else if (requestCode == CAMERA_REQUEST_CODE && grantResults[0] == PERMISSION_GRANTED &&
                grantResults[1] == PERMISSION_GRANTED) {
            openCamera();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mapFragmentActionListener = null;
    }

    private void initViewModel() {
        mapFragmentViewModel = new ViewModelProvider(this, new MapFragmentViewModelFactory(getContext()))
                .get(MapFragmentViewModel.class);
        mapFragmentViewModel.fetchPhoto().observe(getViewLifecycleOwner(), this::showMarkers);
        mapFragmentViewModel.getLatLngMutableLiveData()
                .observe(getViewLifecycleOwner(), this::animateCamera);
        mapFragmentViewModel.getPhotoIdLiveData().observe(getViewLifecycleOwner(), this::openPhoto);
    }

    private void openPhoto(long id) {
        if (mapFragmentActionListener != null) {
            mapFragmentActionListener.onPhotoClickAction(id);
        }
    }

    private void animateCamera(LatLng latLng) {
        if (latLng != null) {
            googleMapOptional.ifPresent(googleMap -> googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10)));
        }
    }

    private void enableLocation() {
        googleMapOptional.ifPresent(googleMap -> {
            mapFragmentViewModel.fetchUserLocation();
            googleMap.setMyLocationEnabled(true);
        });
    }

    private void initMap() {
        Optional.of((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.supportMapFragment))
                .ifPresent(mapFragment -> mapFragment.getMapAsync(this::onMapReady));
    }

    private void onMapReady(GoogleMap googleMap) {
        googleMapOptional = Optional.of(googleMap);
        googleMapOptional.ifPresent(map -> map.setOnMarkerClickListener(marker -> {
            mapFragmentViewModel.openPhoto((Long) marker.getTag());
            return false;
        }));
        checkLocationPermission();
    }

    private void showMarkers(List<MapMarker> mapMarkerList) {
        clearMap();
        googleMapOptional.ifPresent(googleMap -> mapMarkerList.forEach(mapMarker -> {
            Marker marker = googleMap.addMarker(mapMarker.getMarkerOptions());
            marker.setTag(mapMarker.getTag());
        }));
    }

    private void clearMap() {
        googleMapOptional.ifPresent(GoogleMap::clear);
    }

    private void openCamera() {
        Context context = getContext();
        try {
            tempFile = CameraUtils.preparePhotoFile(context);
            Optional<Intent> intentOptional = CameraUtils.prepareCameraIntent(context, tempFile);
            intentOptional.ifPresent(intent -> startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationPermission() {
        if (checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            enableLocation();
        } else {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    private void checkCameraPermission() {
        Context context = getContext();
        if (checkSelfPermission(context, CAMERA) == PERMISSION_GRANTED &&
                checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);
        }
    }
}
