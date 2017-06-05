package com.example.ngudekin.ngudekin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ngudekin.ngudekin.CustomVolleyRequest;
import com.example.ngudekin.ngudekin.R;

public class PhotoViewerActivity extends AppCompatActivity {

    Toolbar customToolbar;
    NetworkImageView image;
    boolean fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fullScreen = true;

        Intent intent = getIntent();
        String imgSrc = intent.getStringExtra("imgSrc");

        image = (NetworkImageView) findViewById(R.id.image);
        customToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        customToolbar.animate().alpha(0).start();

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
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fullScreen) {
                    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    customToolbar.animate().alpha(0).start();
                    fullScreen = true;
                } else {
                    //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    customToolbar.animate().alpha(1).start();
                    fullScreen = false;
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
