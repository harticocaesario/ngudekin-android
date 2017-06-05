package com.example.ngudekin.ngudekin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.toolbox.*;
import com.android.volley.toolbox.ImageLoader;
import com.example.ngudekin.ngudekin.CustomVolleyRequest;
import com.example.ngudekin.ngudekin.R;
import com.example.ngudekin.ngudekin.Utils;

/**
 * Created by TICO on 12/09/2016.
 */

public class RecipeDetailActivity extends AppCompatActivity{
    String title;
    String imgSrc;
    String ingredients;
    String category;
    String steps;
    String source;

    TextView titleView;
    NetworkImageView thumbnailImage;
    TableLayout ingredientsView;
    TableLayout stepsView;
    TextView sourceView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        title = intent.getStringExtra("judul");
        imgSrc = intent.getStringExtra("thumbnail");
        ingredients = intent.getStringExtra("ingredients");
        category = intent.getStringExtra("kategori");
        steps = intent.getStringExtra("langkah");
        source = intent.getStringExtra("link");

        titleView = (TextView) findViewById(R.id.title);
        thumbnailImage = (NetworkImageView) findViewById(R.id.thumbnail);
        ingredientsView = (TableLayout) findViewById(R.id.ingredients);
        stepsView = (TableLayout) findViewById(R.id.langkah);
        sourceView = (TextView) findViewById(R.id.link);

        ImageLoader imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        imageLoader.get(imgSrc, com.android.volley.toolbox.ImageLoader.getImageListener(thumbnailImage, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));


        titleView.setText(title);
        thumbnailImage.setImageUrl(imgSrc,imageLoader);
        thumbnailImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), PhotoViewerActivity.class);
                myIntent.putExtra("imgSrc", imgSrc);
                startActivity(myIntent);
            }
        });

        createPerPoints(ingredientsView, ingredients);
        createPerPoints(stepsView, steps);
        sourceView.setText(source);

    }
    public void onDestroy() {

        super.onDestroy();
        finish();

    }

    private void createPerPoints(TableLayout tableLayout, String steps) {
        String[] splittedSteps = steps.split("\n");

        for (int i = 0; i < splittedSteps.length; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));

            TextView bullet = new TextView(this);
            bullet.setText("\u2022   ");
            bullet.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            bullet.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));

            TextView singleStep = new TextView(this);
            singleStep.setText(splittedSteps[i]);
            singleStep.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            singleStep.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
            singleStep.setPadding(0,0, Utils.convertDpToPx(20, this),0);

            tableRow.addView(bullet);
            tableRow.addView(singleStep);

            tableLayout.addView(tableRow);
        }


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
