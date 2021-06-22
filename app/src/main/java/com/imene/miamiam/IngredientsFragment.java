package com.imene.miamiam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.imene.miamiam.Adapters.IngredientAdapter;
import com.imene.miamiam.Models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {
    private List<Ingredient> lstIngredient = new ArrayList<>();
    private RecyclerView myrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        myrv = RootView.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        IngredientAdapter myAdapter = new IngredientAdapter(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);

        Button searchIngredients = RootView.findViewById(R.id.ingredients_search);
        searchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tmp = IngredientAdapter.listeIngr;
                if(tmp.isEmpty()){
                    Toast.makeText(getActivity(), "You must select at least one ingredient", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent searchResultsIntent = new Intent(getActivity(), RecetteWithIngredientActivity.class);
                    startActivity(searchResultsIntent);
                }
            }
        });


        return RootView;
    }

    private void initializeIngredients() {
        lstIngredient.add(new Ingredient("Beef", "beef-cubes-raw.png"));
        lstIngredient.add(new Ingredient("Fish", "fish-fillet.jpg"));
        lstIngredient.add(new Ingredient("Chicken", "chicken-breasts.png"));
        lstIngredient.add(new Ingredient("Tuna", "canned-tuna.png"));
        lstIngredient.add(new Ingredient("Flour", "flour.png"));
        lstIngredient.add(new Ingredient("Rice", "uncooked-white-rice.png"));
        lstIngredient.add(new Ingredient("Pasta", "fusilli.jpg"));
        lstIngredient.add(new Ingredient("Cheese", "cheddar-cheese.png"));
        lstIngredient.add(new Ingredient("Butter", "butter.png"));
        lstIngredient.add(new Ingredient("Bread", "white-bread.jpg"));
        lstIngredient.add(new Ingredient("Onion", "brown-onion.png"));
        lstIngredient.add(new Ingredient("Garlic", "garlic.jpg"));
        lstIngredient.add(new Ingredient("Milk", "milk.png"));
        lstIngredient.add(new Ingredient("Eggs", "egg.png"));
        lstIngredient.add(new Ingredient("Oil", "vegetable-oil.jpg"));
        lstIngredient.add(new Ingredient("Yogurt", "plain-yogurt.jpg"));
        lstIngredient.add(new Ingredient("Salt", "salt.jpg"));
        lstIngredient.add(new Ingredient("Sugar", "sugar-in-bowl.png"));
        lstIngredient.add(new Ingredient("Pepper", "pepper.jpg"));
        lstIngredient.add(new Ingredient("Water", "water.jpg"));
        lstIngredient.add(new Ingredient("Parsley", "parsley.jpg"));
        lstIngredient.add(new Ingredient("Basil", "basil.jpg"));
        lstIngredient.add(new Ingredient("Chocolate", "milk-chocolate.jpg"));
        lstIngredient.add(new Ingredient("Nuts", "hazelnuts.png"));
        lstIngredient.add(new Ingredient("Tomato", "tomato.png"));
        lstIngredient.add(new Ingredient("Cucumber", "cucumber.jpg"));
        lstIngredient.add(new Ingredient("Bell pepper", "red-bell-pepper.jpg"));
        lstIngredient.add(new Ingredient("Mushrooms", "portabello-mushrooms.jpg"));
        lstIngredient.add(new Ingredient("Lemon", "lemon.jpg"));
        lstIngredient.add(new Ingredient("Orange", "orange.jpg"));
        lstIngredient.add(new Ingredient("Banana", "bananas.jpg"));
        lstIngredient.add(new Ingredient("Wine", "red-wine.jpg"));
        lstIngredient.add(new Ingredient("Apple", "apple.jpg"));
        lstIngredient.add(new Ingredient("Berries", "berries-mixed.jpg"));
        lstIngredient.add(new Ingredient("Biscuits", "buttermilk-biscuits.jpg"));
        lstIngredient.add(new Ingredient("Pineapple", "pineapple.jpg"));
    }



}