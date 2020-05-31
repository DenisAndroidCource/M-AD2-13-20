package com.example.maps.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.maps.MainActivity;
import com.example.maps.R;
import com.example.maps.domain.Photo;
import com.example.maps.repo.PhotoRepository;
import com.example.maps.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class PhotoListWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private String packageName;
    private PhotoRepository photoRepository;
    private Optional<List<Photo>> photoList;

    PhotoListWidgetRemoteViewFactory(String packageName, PhotoRepository photoRepository) {
        this.packageName = packageName;
        this.photoRepository = photoRepository;
        this.photoList = Optional.empty();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        try {
            photoList = Optional.ofNullable(photoRepository.getPhoto().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return photoList.map(List::size).orElse(0);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = null;
        if (photoList.isPresent()) {
            Photo photo = photoList.get().get(position);
            remoteViews = new RemoteViews(packageName, R.layout.item_widget_photo);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            remoteViews.setTextViewText(R.id.imageTitle, simpleDateFormat.format(photo.getDate()));

            Bitmap bitmap = ImageUtils.fileToBitmap(photo.getFile());
            Bitmap proxy = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(proxy);
            canvas.drawBitmap(bitmap, new Matrix(), null);
            remoteViews.setImageViewBitmap(R.id.imagePreview, proxy);

            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXTRA_OPEN_PHOTO_ID, photo.getId());
            remoteViews.setOnClickFillInIntent(R.id.imagePreviewContainer, intent);
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return photoList.map(photoList -> photoList.get(position).getId()).orElse(0L);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
