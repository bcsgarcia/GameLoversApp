package com.gameloverchallenge.brunogarcia.bcsggamelover.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    private Long id;
    private String name;
    private String summary;
    private List<String> platforms;
    private List<String> genres;
    private String cover;
    private Long width;
    private Long height;

    private String TAG = "Game";

    public Game(){}

    public Game(JSONObject jsonObject){

        try {
            this.id = Long.parseLong(jsonObject.getString("id"));
        } catch (JSONException e){
            Log.e(TAG, "Erro carregar ID: " + e.getMessage());
        }

        try {
            this.name = jsonObject.getString("name");
        } catch (JSONException e){
            Log.e(TAG, "Erro carregar NAME: " + e.getMessage());
        }

        try {
            this.summary = jsonObject.getString("summary");
        } catch (JSONException e){
            Log.e(TAG, "Erro carregar SUMMARY: " + e.getMessage());
        }

        try {
            this.platforms = new ArrayList<>();
            for (int i=0; i < jsonObject.getJSONArray("platforms").length(); i++)
                //Como minha conta é gratuíta eu atingi o limite de 100 consultas com expand
                //por isso será necessário mais uma requisição para recuperar os nomes das plataformas deste jogo
                //this.platforms.add( ((JSONObject)jsonObject.getJSONArray("platforms").get(i)).getString("name") );
                this.platforms.add( jsonObject.getJSONArray("platforms").getString(i));
        } catch (JSONException e){
            Log.e(TAG, "Erro carregar PLATFORMS: " + e.getMessage());
        }

        try {
            this.genres = new ArrayList<>();
            for (int i=0; i < jsonObject.getJSONArray("genres").length(); i++)
                //Como minha conta é gratuíta eu atingi o limite de 100 consultas com expand
                //por isso será necessário mais uma requisição para recuperar os nomes dos genres deste jogo
                //this.genres.add( ((JSONObject)jsonObject.getJSONArray("genres").get(i)).getString("name") );
                this.genres.add( jsonObject.getJSONArray("genres").getString(i) );
        } catch (JSONException e){
            Log.e(TAG, "Erro carregar GENRES: " + e.getMessage());
        }

        try {
            this.cover = jsonObject.getJSONObject("cover").getString("url");
            this.width = Long.parseLong(jsonObject.getJSONObject("cover").getString("width"));
            this.height = Long.parseLong(jsonObject.getJSONObject("cover").getString("height"));
        } catch (JSONException e) {
            Log.e(TAG, "Erro carregar COVER: " + e.getMessage());
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
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

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
