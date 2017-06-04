package com.example.ngudekin.ngudekin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ngudekin.ngudekin.AppController;
import com.example.ngudekin.ngudekin.MyRecyclerViewAdapter;
import com.example.ngudekin.ngudekin.R;
import com.example.ngudekin.ngudekin.Recipe;

//import info.androidhive.volleyjson.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TICO on 11/16/2016.
 */
public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Recipe> recipeList;
    private static String TAG = SearchResultActivity.class.getSimpleName();
    private final String urlJsonArray= "http://mosqcode.net/ngudek/search/";
    Button tombol;
    CheckBox food;
    CheckBox drink;
    Intent myIntent;
    String checkedCategory = "";
    String query ="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        recipeList.clear();

        Intent intent = getIntent();
        checkedCategory = intent.getStringExtra("cekbox");
        query = intent.getStringExtra("isi");

        final EditText searchInput =  (EditText) findViewById(R.id.searchInput);
        tombol = (Button) findViewById(R.id.search_button);
        searchInput.setText(query);

        food = (CheckBox)findViewById(R.id.makanan);
        food.setChecked(checkedCategory.equals("semua") || checkedCategory.equals(food.getText().toString()));
        drink = (CheckBox)findViewById(R.id.minuman);
        drink.setChecked(checkedCategory.equals("semua") || checkedCategory.equals(drink.getText().toString()));

        adapter = new MyRecyclerViewAdapter(recipeList, this);
        mRecyclerView.setAdapter(adapter);
        makeJsonArrayRequest();

        tombol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checkedCategory = "";

                if(food.isChecked()&& drink.isChecked()){
                    checkedCategory = "semua";
                } else if(food.isChecked()){
                    checkedCategory = food.getText().toString();
                } else if(drink.isChecked()){
                    checkedCategory = drink.getText().toString();
                }

                if(checkedCategory.equals("")){
                    Toast.makeText(getApplicationContext(),
                        "Pilih Kategori",
                        Toast.LENGTH_SHORT).show();
                } else {

                    query = searchInput.getText().toString();
                    recipeList.clear();

                    makeJsonArrayRequest();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void makeJsonArrayRequest() {

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArray + trimComma(query),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int i = 0; i < response.length(); i++){
                                JSONObject product = (JSONObject) response
                                        .get(i);
                                if(checkedCategory.equals(product.getString("kategori")) || (checkedCategory.equals("semua"))) {
                                    String nama = product.getString("nama");
                                    String kategori = product.getString("kategori");
                                    String bahan = product.getString("list_bahan");
                                    String langkah = product.getString("langkah");
                                    String link = product.getString("link");
                                    String gambar = product.getString("gambar");
                                    Recipe resep1 = new Recipe(nama, bahan, gambar, kategori, langkah, link);
                                    recipeList.add(resep1);
                                }
                            }


                            if(response.length()==0 || recipeList.isEmpty()){
                                Toast.makeText(getApplicationContext(),
                                        "Tidak ada hasil",
                                        Toast.LENGTH_SHORT).show();

                            }
                            //txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        myIntent = new Intent(getApplicationContext(), MainActivity.class);

    }

    private String trimComma(String query) {
        query = query.replaceAll(", ",",%20");
        query = query.replaceAll(",",",%20");
        query = query.replaceAll(" ","%20");
        return query;
    }

}


