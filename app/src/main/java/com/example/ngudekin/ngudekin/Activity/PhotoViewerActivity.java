package com.example.ngudekin.ngudekin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ngudekin.ngudekin.CustomVolleyRequest;
import com.example.ngudekin.ngudekin.R;

import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoViewerActivity extends AppCompatActivity {

    Toolbar customToolbar;
    NetworkImageView image;
    PhotoViewAttacher photoViewAttacher;
    boolean showBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        showBack = false;

        Intent intent = getIntent();
        String imgSrc = intent.getStringExtra("imgSrc");

        image = (NetworkImageView) findViewById(R.id.image);

        customToolbar = (Toolbar) findViewById(R.id.custom_toolbar);

        setTitle("");
        setSupportActionBar(customToolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        ImageLoader imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        imageLoader.get(imgSrc, com.android.volley.toolbox.ImageLoader.getImageListener(image, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        image.setImageUrl(imgSrc,imageLoader);
        image.setScaleType(ImageView.ScaleType.CENTER);
        photoViewAttacher = new PhotoViewAttacher(image);
        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                if (!showBack) {
                    customToolbar.animate().alpha(0).start();
                    showBack = true;
                } else {
                    customToolbar.animate().alpha(1).start();
                    showBack = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
