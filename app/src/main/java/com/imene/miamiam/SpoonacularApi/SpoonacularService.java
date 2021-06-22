package com.imene.miamiam.SpoonacularApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class SpoonacularService {

    public SpoonacularService() {
    }
    Volley client = new Volley();
    public void searchRecette(String search, RequestQueue requestQueue) {

       /* JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "\"https://api.spoonacular.com/recipes/search?query=\" + search + \"&number=30&instructionsRequired=true&apiKey=b93a882d6d514ea9b42b9b28b5236005\"",
              null

               );*/


        //client.newRequestQueue(requestQueue).en


    }
}
