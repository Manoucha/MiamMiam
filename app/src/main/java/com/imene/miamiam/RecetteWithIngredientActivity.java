package com.imene.miamiam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.imene.miamiam.Adapters.IngredientAdapter;
import com.imene.miamiam.Adapters.RecetteRecycAdapter;
import com.imene.miamiam.Models.Recette;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecetteWithIngredientActivity extends AppCompatActivity {
    private TextView ingredients_list;
    private RecyclerView myrv;
    private JSONArray testArr;
    private List<Recette> lstRecipe = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette_with_ingredient);
       // ingredients_list= findViewById(R.id.ingredients_names_list);
        progressBar = findViewById(R.id.progressbar3);
        String searchText=getStringFromList(IngredientAdapter.listeIngr);
        Log.d("ingredient à injecter ",""+searchText);
     //   ingredients_list.setText(searchText);
        getResults(searchText);
    }

    private void getResults(String searchText) {
        myrv = findViewById(R.id.recycleview_ingredients_search_result);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        String URL = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="+searchText+"&number=30&instructionsRequired=true&apiKey=b93a882d6d514ea9b42b9b28b5236005";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            testArr = response;
                            Log.i("the res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                lstRecipe.add(new Recette(jsonObject1.optString("id"),jsonObject1.optString("title"),jsonObject1.optString("image"), 0, 0));
                            }
                            progressBar.setVisibility(View.GONE);
                            RecetteRecycAdapter myAdapter = new RecetteRecycAdapter(getApplicationContext(), lstRecipe);
                            myrv.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private String getStringFromList(List<String> ingredientsList) {
        StringBuilder result= new StringBuilder(ingredientsList.get(0));
        for (int i=1;i < ingredientsList.size();i++)
        {
            result.append(", ").append(ingredientsList.get(i));
        }
        return result.toString();
    }
}