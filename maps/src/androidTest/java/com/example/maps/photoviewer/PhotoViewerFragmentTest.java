package com.example.maps.photoviewer;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.action.ViewActions;

import com.example.maps.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoViewerFragmentTest {

    private static final String TEXT_DATE = "05.05.2020";

    @Rule
    public TestRule liveDataRule = new InstantTaskExecutorRule();

    private PhotoViewerFragmentViewModel viewModel = mock(PhotoViewerFragmentViewModel.class);
    private PhotoViewState photoViewState = new PhotoViewState(Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888), TEXT_DATE);
    private MutableLiveData<PhotoViewState> photoLiveData = new MutableLiveData<>();

    @Before
    public void setUp() {
        photoLiveData.setValue(photoViewState);
    }

    @Test
    public void whenStartFragmentThenImageDisplayed() {
        launchFragment();
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withText(TEXT_DATE)).check(matches(isDisplayed()));
    }

    @Test
    public void whenTitleClickedThenImageHidden() {
        launchFragment();
        onView(withText(TEXT_DATE)).perform(ViewActions.click());
        onView(withId(R.id.imageView)).check(matches(not(isDisplayed())));
    }

    private void launchFragment() {
        when(viewModel.fetchPhoto(anyLong())).thenReturn(photoLiveData);
        launchInContainer(PhotoViewerFragment.class, null, new PhotoViewerFragmentFactory());
    }

    private class PhotoViewerFragmentFactory extends FragmentFactory {
        @NonNull
        @Override
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
            PhotoViewerFragment fragment = PhotoViewerFragment.newInstance(0);
            fragment.setViewModelFactory(ViewModelUtil.create(viewModel));
            return fragment;
        }
    }
}