package com.example.ngudekin.ngudekin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ngudekin.ngudekin.Activity.RecipeDetailActivity;

import java.util.List;

/**
 * Created by TICO on 11/21/2016.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private ImageLoader imageLoader;
    private List<Recipe> recipes;
    private Context mContext;
    Intent myIntent;


    public MyRecyclerViewAdapter(List<Recipe> recipes, Context mContext) {
        this.recipes = recipes;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_cardview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
        imageLoader.get(recipe.getImageSource(), ImageLoader.getImageListener(holder.thumbnail, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.thumbnail.setImageUrl(recipe.getImageSource(), imageLoader);
        holder.title.setText(recipe.getTitle());
        holder.ingredients.setText(recipe.getSummarizedIngredients());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RecipeDetailActivity.class);

                myIntent.putExtra("judul", recipe.getTitle());
                myIntent.putExtra("thumbnail", recipe.getImageSource());
                myIntent.putExtra("ingredients", recipe.getIngredients());

                myIntent.putExtra("kategori", recipe.getKategori());
                myIntent.putExtra("langkah", recipe.getLangkah());
                myIntent.putExtra("link", recipe.getLink());

                mContext.startActivity(myIntent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected NetworkImageView thumbnail;
        protected TextView title;
        protected TextView ingredients;

        public CustomViewHolder(View view) {
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.cardview);
            this.thumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
            this.ingredients = (TextView) view.findViewById(R.id.ingredients);
        }
    }
}
