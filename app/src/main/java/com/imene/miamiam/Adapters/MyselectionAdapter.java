package com.imene.miamiam.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.imene.miamiam.Models.Recette;
import com.imene.miamiam.R;
import com.imene.miamiam.RecetteDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyselectionAdapter  extends RecyclerView.Adapter<MyselectionAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recette> AllRecettesList;

    public MyselectionAdapter(Context mContext, List<Recette> data) {
        this.mContext = mContext;
        this.AllRecettesList = data;
    }

    @NonNull
    @Override
    public MyselectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.myselection_item, parent, false);
        return new MyselectionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyselectionAdapter.MyViewHolder holder, final int position) {

        holder.tv_recipe_title.setText(AllRecettesList.get(position).getTitle());

        if (AllRecettesList.get(position).getThumbnail().isEmpty()) {
            //si jamais y a pas d'image!
            holder.img_recipe_thumbnail.setImageResource(R.drawable.nopicture);
        } else{
            Picasso.get().load(AllRecettesList.get(position).getThumbnail()).into(holder.img_recipe_thumbnail);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecetteDetailActivity.class);
                intent.putExtra("id", AllRecettesList.get(position).getId());
                intent.putExtra("img", AllRecettesList.get(position).getThumbnail());
                intent.putExtra("title", AllRecettesList.get(position).getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllRecettesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_recipe_thumbnail;
        CardView cardView;
        TextView tv_recipe_title;


        public MyViewHolder(View itemView) {
            super(itemView);
            img_recipe_thumbnail = itemView.findViewById(R.id.favorites_recipe_img);
            cardView = itemView.findViewById(R.id.favorites_cardvieww);
            tv_recipe_title = itemView.findViewById(R.id.favorites_recipe_title);
          ;
        }
    }
}
