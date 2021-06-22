package com.imene.miamiam.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.imene.miamiam.Models.Recette;
import com.imene.miamiam.R;
import com.imene.miamiam.RecetteDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecetteRecycAdapter extends RecyclerView.Adapter<RecetteRecycAdapter.MyViewHolder>  {

    private Context mContext ;
    private List<Recette> AllRecettesList;

    public RecetteRecycAdapter(Context mContext, List<Recette> data) {
        this.mContext = mContext;
        this.AllRecettesList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recette_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.tv_recipe_title.setText(AllRecettesList.get(position).getTitle());
        holder.tv_amount_of_dishes.setText(Integer.toString(AllRecettesList.get(position).getAmountOfDishes()) );
        holder.tv_ready_in_mins.setText( Integer.toString(AllRecettesList.get(position).getReadyInMins()) );
        if (AllRecettesList.get(position).getThumbnail().isEmpty()) {
            holder.img_recipe_thumbnail.setImageResource(R.drawable.nopicture);
        } else{
            Picasso.get().load(AllRecettesList.get(position).getThumbnail()).into(holder.img_recipe_thumbnail);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, RecetteDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", AllRecettesList.get(position).getId());
                intent.putExtra("title", AllRecettesList.get(position).getTitle());
                intent.putExtra("img", AllRecettesList.get(position).getThumbnail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllRecettesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_recipe_title,tv_amount_of_dishes,tv_ready_in_mins;
        ImageView img_recipe_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_recipe_title = (TextView) itemView.findViewById(R.id.recipe_title_id) ;
            img_recipe_thumbnail = (ImageView) itemView.findViewById(R.id.recipe_img_id);
            tv_amount_of_dishes = (TextView) itemView.findViewById(R.id.servingTvLeft);
            tv_ready_in_mins = (TextView) itemView.findViewById(R.id.readyInTvRight);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
