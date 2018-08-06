package com.gameloverchallenge.brunogarcia.bcsggamelover.entity;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gameloverchallenge.brunogarcia.bcsggamelover.net.DataCallback;
import com.gameloverchallenge.brunogarcia.bcsggamelover.net.NetworkController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Platform implements Serializable {

    private final String TAG = "Platform";

    private Long id;
    private String name;
    private List<Long> games;

    public Platform(){}

    public Platform(Long id, String name, List<Long> games){
        this.id = id;
        this.name = name;
        this.games = games;
    }

    public Platform(JSONObject jsonObject){
        try {
            this.id = Long.parseLong(jsonObject.getString("id"));
            this.name = jsonObject.getString("name");
            games = new ArrayList<>();
            for (int i=0; i < jsonObject.getJSONArray("games").length(); i++){
                games.add( Long.parseLong(jsonObject.getJSONArray("games").get(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getGames() {
        return games;
    }

    public void setGames(List<Long> games) {
        this.games = games;
    }


}
