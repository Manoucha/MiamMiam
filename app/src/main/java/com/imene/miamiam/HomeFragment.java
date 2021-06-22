package com.imene.miamiam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.imene.miamiam.Adapters.RecetteRecycAdapter;
import com.imene.miamiam.Models.Recette;
import com.imene.miamiam.SpoonacularApi.SpoonacularService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;


public class HomeFragment extends Fragment {

    private ImageButton searchBtn;
    private TextView searchTv, noresultTV;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private List<Recette> listeRecettes = new ArrayList<>();
    private List<Recette> listchercherRecette;
    private JSONArray testArr;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        noresultTV = view.findViewById(R.id.empty_view2);
        recyclerView = view.findViewById(R.id.recyclerview);

        progressBar = view.findViewById(R.id.progressbar2);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getRandomRecipes();
        searchTv = view.findViewById(R.id.home_search_et);
        searchBtn = view.findViewById(R.id.home_search_btn);

        searchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!v.getText().toString().equals("")) {
                        noresultTV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        recyclerView.setAlpha(0);
                        finRecette(v.getText().toString());
                    }
                    else
                        Toast.makeText(getContext(), "Non pas le vide!...", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void finRecette(String search) {
        listchercherRecette = new ArrayList<Recette>();
       String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=b93a882d6d514ea9b42b9b28b5236005";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("results");
                            Log.i("the search res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                listchercherRecette.add(new Recette(jsonObject1.optString("id"),jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            progressBar.setVisibility(View.GONE);
                            if(listchercherRecette.isEmpty()){
                                recyclerView.setAlpha(0);
                                noresultTV.setVisibility(View.VISIBLE);
                            }
                            else{
                                noresultTV.setVisibility(View.GONE);
                                RecetteRecycAdapter myAdapter = new RecetteRecycAdapter(getContext(), listchercherRecette);
                                recyclerView.setAdapter(myAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAlpha(1);
                            }
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

        /*SpoonacularService service = new SpoonacularService();
        service.searchRecette(search, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                testArr = (JSONArray) response.body().get("results");
                Log.i("the search res is:", String.valueOf(testArr));
                for (int i = 0; i < testArr.length(); i++) {
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = testArr.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listchercherRecette.add(new Recette(jsonObject1.optString("id"),jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                }
                progressBar.setVisibility(View.GONE);
                if(listchercherRecette.isEmpty()){
                    recyclerView.setAlpha(0);
                    noresultTV.setVisibility(View.VISIBLE);
                }
                else{
                    noresultTV.setVisibility(View.GONE);
                    RecetteRecycAdapter myAdapter = new RecetteRecycAdapter(getContext(), listchercherRecette);
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAlpha(1);

            }}
        });*/


    }

    private void getRandomRecipes() {
        String URL = "https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&apiKey=b93a882d6d514ea9b42b9b28b5236005";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("recipes");
                            Log.i("the res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                listeRecettes.add(new Recette(jsonObject1.optString("id"),jsonObject1.optString("title"), jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            progressBar.setVisibility(View.GONE);
                            RecetteRecycAdapter myAdapter = new RecetteRecycAdapter(getContext(), listeRecettes);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setAlpha(0);
                        noresultTV.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }




}