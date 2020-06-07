package com.example.maps.map;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.lifecycle.MutableLiveData;

import com.example.maps.domain.Photo;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.utils.LocationController;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapFragmentViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private PhotoRepository photoRepositoryMock = mock(PhotoRepository.class);
    private LocationController locationControllerMock = mock(LocationController.class);
    private Function<List<Photo>, List<MapMarker>> mapper = new MapMarkerMapper();

    private MapFragmentViewModel mapFragmentViewModel = new MapFragmentViewModel(photoRepositoryMock,
            locationControllerMock, mapper);

    private LatLng location = new LatLng(0.0, 0.0);
    private Photo photo = new Photo(1L, new File(""), new LatLng(0.0, 0.0), new Date());

    @Test
    public void whenOpenPhotoThenCorrectIdPassed() {
        mapFragmentViewModel.openPhoto(10L);

        assertEquals(Long.valueOf(10L), mapFragmentViewModel.getPhotoIdLiveData().getValue());
    }

    /**
     * When fetch user location then location controller gives the correct location
     */
    @Test
    public void whenFetchUserLocationThenTheCorrectLocationPassed() {
        when(locationControllerMock.getUserLocation()).thenReturn(location);

        mapFragmentViewModel.fetchUserLocation();

        assertEquals(location, mapFragmentViewModel.getLatLngMutableLiveData().getValue());
        verify(locationControllerMock, times(1)).getUserLocation();
    }

//    @Test
//    public void whenFetchPhotoThenLiveDataContainsPhoto() {
//        List<Photo> photoList = Arrays.asList(photo, photo, photo, photo, photo);
//        MutableLiveData<List<Photo>> liveData = getPhotoListLiveData(photoList);
//        when(photoRepositoryMock.getPhotoLiveData()).thenReturn(liveData);
//
//        LiveData<List<MapMarker>> actualLiveData = mapFragmentViewModel.fetchPhoto();
//
//        assertEquals(photoList.size(), actualLiveData.getValue().size());
//        verify(photoRepositoryMock, times(1)).getPhotoLiveData();
//        verify(mapper, times(1)).apply(photoList);
//    }

    private MutableLiveData<List<Photo>> getPhotoListLiveData(List<Photo> photoList) {
        MutableLiveData<List<Photo>> liveData = new MutableLiveData<>();
        liveData.setValue(photoList);
        return liveData;
    }
}