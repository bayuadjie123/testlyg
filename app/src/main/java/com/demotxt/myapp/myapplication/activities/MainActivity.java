package com.demotxt.myapp.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.adapters.RecyclerViewAdapter;
import com.demotxt.myapp.myapplication.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String JSON_URL = "https://dummyjson.com/products" ;
    private JsonObjectRequest request ;
    private RequestQueue requestQueue ;
    private List<Anime> lstAnime ;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstAnime = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();



    }

    private void jsonrequest() {

        request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonObject  = null ;
                JSONArray userArray = null;
                try {
                    userArray = response.getJSONArray("products");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0 ; i < userArray.length(); i++ ) {


                    try {
                        jsonObject = userArray.getJSONObject(i) ;
                        Anime anime = new Anime() ;
                        anime.setName(jsonObject.getString("title"));
                        anime.setDescription(jsonObject.getString("description"));
                        anime.setRating(jsonObject.getString("rating"));
                        anime.setCategorie(jsonObject.getString("brand"));
                        anime.setCategorie(jsonObject.getString("stock"));
                        anime.setCategorie(jsonObject.getString("category"));
                        anime.setNb_episode(1);
                        anime.setStudio(jsonObject.getString("price"));
                        anime.setImage_url(jsonObject.getString("thumbnail"));
                        lstAnime.add(anime);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(lstAnime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request) ;


    }

    private void setuprecyclerview(List<Anime> lstAnime) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lstAnime) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
}