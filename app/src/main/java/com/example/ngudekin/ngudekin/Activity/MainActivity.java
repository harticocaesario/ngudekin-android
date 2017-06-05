package com.example.ngudekin.ngudekin.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngudekin.ngudekin.Activity.SearchResultActivity;
import com.example.ngudekin.ngudekin.ImageLoader;
import com.example.ngudekin.ngudekin.R;

public class MainActivity  extends AppCompatActivity  {

    Button tombol;
    CheckBox food;
    CheckBox drink;
    Intent myIntent;
    String checkedCategory = "";
    final Context context= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tombol = (Button) findViewById(R.id.search_button);

        ImageLoader imgLoader = new ImageLoader(getApplicationContext());

        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        //imgLoader.DisplayImage(image_url, loader, image);

        food = (CheckBox)findViewById(R.id.makanan);
        food.setChecked(true);
        drink = (CheckBox)findViewById(R.id.minuman);
        drink.setChecked(true);

        final EditText searchInput = (EditText) findViewById(R.id.searchInput);

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchInput.clearFocus();
                    performSearch(v.getText().toString());

                    return true;
                }
                return false;
            }
        });


        tombol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                searchInput.clearFocus();
                performSearch(searchInput.getText().toString());
            }
        });
    }

    private void performSearch(String query){
        myIntent = new Intent(getApplicationContext(), SearchResultActivity.class);

        if (food.isChecked() && drink.isChecked()) {
            checkedCategory = "semua";
        } else if (food.isChecked()) {
            checkedCategory = food.getText().toString();
        } else if (drink.isChecked()) {
            checkedCategory = drink.getText().toString();
        }

        if(checkedCategory.equals("")){Toast.makeText(getApplicationContext(),
                "Pilih Kategori Makanan/Minuman",
                Toast.LENGTH_SHORT).show();}
        else {
            myIntent.putExtra("cekbox", checkedCategory);

            myIntent.putExtra("isi", query);
            startActivity(myIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
