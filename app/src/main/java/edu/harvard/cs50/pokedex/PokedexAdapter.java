package edu.harvard.cs50.pokedex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {
    //static data
    /*    private List<Pokemon> pokemon = Arrays.asList(
            new Pokemon("Bulbasaur",1),
            new Pokemon("Ivysaur",2),
            new Pokemon("Venusaur",3)
    );
    */
    private List<Pokemon> pokemon = new ArrayList<>();
    private RequestQueue requestQueue;

    //Pass context from MainActivity to PokedexAdapter.java
    PokedexAdapter(Context context){
        requestQueue = Volley.newRequestQueue(context);
        loadPokemon();
    }
    public void loadPokemon(){
        String strUrl = "https://pokeapi.co/api/v2/pokemon?limit=191";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for(int i=0; i<results.length(); i++){
                        JSONObject result = results.getJSONObject(i);
                        String name = capitalize( result.getString("name") );
                        String url = result.getString("url");
                        pokemon.add(new Pokemon(name, url));
                    }
                    notifyDataSetChanged();
                } catch (JSONException jex) {
                    Log.e("cs50log", "Json error", jex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cs50log", "Pokemon list error", error);
            }
        });
        requestQueue.add(request);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PokedexViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public LinearLayout containerView;
        public TextView textView;
        PokedexViewHolder(View view){
            super(view);
            containerView = view.findViewById(R.id.pokedex_row);
            textView = view.findViewById(R.id.pokedex_row_text_view);
            //OnClickListener
            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pokemon clicked
                    Pokemon current = (Pokemon) containerView.getTag();
                    //Click to go to PokemonActivity.class
                    Intent intent = new Intent(v.getContext(),PokemonActivity.class);
                    //intent.putExtra("name", current.getName());
                    //intent.putExtra("number", current.getNumber());
                    intent.putExtra("url", current.getUrl());
                    //Go to new activity
                    v.getContext().startActivity(intent);
                }
            });
        }
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokedex_row, parent,false);
        return new PokedexViewHolder(view);
    }
    // Replace the contents of a view (invoked by the layout manager)
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(@NonNull PokedexAdapter.PokedexViewHolder holder, int position) {
        //public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Pokemon current = pokemon.get(position);
        holder.textView.setText(current.getName());
        holder.containerView.setTag(current);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemon.size();
    }
    public String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
