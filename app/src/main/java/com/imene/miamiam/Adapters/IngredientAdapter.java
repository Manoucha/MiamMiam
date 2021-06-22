package com.imene.miamiam.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.imene.miamiam.Models.Ingredient;
import com.imene.miamiam.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {
    private Context mContext;
    public static List<String> listeIngr;

    private List<Ingredient> AllIngredientsList;


    public IngredientAdapter(Context mContext, List<Ingredient> listAllIngredients) {
        this.mContext = mContext;
        listeIngr = new ArrayList<>();
        this.AllIngredientsList = listAllIngredients;

    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);

        View v = mInflater.inflate(R.layout.item_ingredient, parent, false);
        return new IngredientAdapter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final IngredientAdapter.MyViewHolder holder, final int position) {


        holder.tv_ingredient_name.setText(AllIngredientsList.get(position).getName());

        Picasso.get().load(AllIngredientsList.get(position).getThumbnail()).into(holder.img_ingredient_thumbnail);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AllIngredientsList.get(position).isSelected())
                {
                    Log.d("im here ","cliccccked 2 ");

                    holder.cardView.setCardBackgroundColor(Color.parseColor("#00BCD4"));
                    listeIngr.add(AllIngredientsList.get(position).getName());
                } else if (AllIngredientsList.get(position).isSelected())
                {
                    Log.d("im here ","cliccccked 3 ");

                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                    listeIngr.remove(AllIngredientsList.get(position).getName());
                }
                AllIngredientsList.get(position).setSelected();
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllIngredientsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutl;
        CardView cardView;

        TextView tv_ingredient_name;
        ImageView img_ingredient_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            layoutl = itemView.findViewById(R.id.ingredient_layout);
            cardView = (CardView) itemView.findViewById(R.id.cardview_ingredient);
            tv_ingredient_name = (TextView) itemView.findViewById(R.id.ingredient_name);
            img_ingredient_thumbnail = (ImageView) itemView.findViewById(R.id.ingredient_img);

        }
    }
}
