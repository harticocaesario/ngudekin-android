package com.example.ngudekin.ngudekin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TICO on 11/21/2016.
 */

public class Recipe {
    private String title;
    private String ingredients;
    private String imageSource;
    private String kategori;
    private String langkah;
    private String link;


    public Recipe(String title, String ingredients, String imageSource, String kategori, String langkah, String link) {
        this.title = title;
        this.ingredients = ingredients;
        this.imageSource = imageSource;
        this.kategori= kategori;
        this.langkah = langkah;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }
    public String getKategori() {
        return kategori;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getImageSource() {

        return imageSource;
    }

    public void setImageSource(String imageSource) {

        this.imageSource = imageSource;
    }

    public String getLangkah() {

        return langkah;
    }

    public String getSummarizedIngredients(){

        String summarized = ingredients;
        summarized = summarized.replaceAll("(,| +(yang|untuk)).+((?=\\r\\n)|$)", "");
        summarized = summarized.replaceAll(" secukupnya(?=($|\\r\\n))", "");
        summarized = summarized.replaceAll("\r\n", ", ");
        summarized = summarized.replaceAll("((?<=, )|^)(\\d+(\\/\\d+)*|\\?) *(\\w+( teh)*) +", "");

        summarized = summarized.replaceAll(",,", ",");
        summarized = summarized.replaceAll("(?!\\w),(?=\\w)", ", ");
        summarized = summarized.trim();
        summarized = summarized.toLowerCase();
        summarized = (summarized.charAt(0) + "").toUpperCase() + summarized.substring(1);
        return summarized;
    }

}
