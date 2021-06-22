package com.imene.miamiam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imene.miamiam.Models.Ingredient;
import com.imene.miamiam.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class IngredientRecetteAdapter extends RecyclerView.Adapter<IngredientRecetteAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ingredient> AllIngredientsList;
    public static List<String> ingredientsList;

    public IngredientRecetteAdapter(Context mContext, List<Ingredient> data) {
        this.AllIngredientsList = data;
        this.mContext = mContext;
        ingredientsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public IngredientRecetteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.ingredient_item, parent, false);
        return new IngredientRecetteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientRecetteAdapter.MyViewHolder holder, final int position) {
        holder.tv_ingredient_name.setText(AllIngredientsList.get(position).getName());

        Picasso.get().load(AllIngredientsList.get(position).getThumbnail()).into(holder.img_ingredient_thumbnail);
    }

    @Override
    public int getItemCount() {
        return AllIngredientsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ingredient_name;
        ImageView img_ingredient_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_ingredient_name = itemView.findViewById(R.id.recipe_ingredient_name);
            img_ingredient_thumbnail = itemView.findViewById(R.id.recipe_ingredient_img);
        }
    }
}
