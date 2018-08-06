package com.gameloverchallenge.brunogarcia.bcsggamelover.cache;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;


import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Game;
import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Platform;
import com.gameloverchallenge.brunogarcia.bcsggamelover.net.NetworkController;
import com.gameloverchallenge.brunogarcia.bcsggamelover.util.Helper;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.adapter.GamesAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CacheData {

    private static Context mCtx;
    private final String TAG = "CacheData";
    private List<Platform> mPlatforms;
    private Long lastGetPlatform;
    private HashMap<Long, List<Game>> mGamesPlatform = new HashMap<>();
    private static CacheData INSTANCE;

    public static CacheData getInstance(Context ctx) {
        if (INSTANCE == null)  INSTANCE = new CacheData();
        mCtx = ctx;
        return INSTANCE;
    }

    public List<Platform> getListPlatforms() {
        return mPlatforms;
    }

    public void populateListPlatforms() {
        long currTime = new Date().getTime();
        if (lastGetPlatform == null || currTime - lastGetPlatform >= Helper.MINUTE * 5) {
            lastGetPlatform = currTime;
            mPlatforms = new ArrayList<>();
            NetworkController.getInstance(mCtx).fetchData("platforms/29,19,4,9,12,49,48?fields=id,name,games&order=games.name",
                    (JSONArray result) -> {
                        Log.d(TAG, result.toString());
                        for (int i = 0; i < result.length(); i++) {
                            try {
                                mPlatforms.add(new Platform(result.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                });
        }
    }

    public void populateGames(String Ids, final Platform platform, final GamesAdapter adapter, final ProgressDialog pd){
        Helper.fetchingGamesData = true;
        NetworkController.getInstance(mCtx).fetchData(String.format("games/%s%n?fields=id,name,genres,platforms,cover,summary",Ids) , //&expand=genres,platforms
                (JSONArray result) -> {
                    List<Game> listGames = mGamesPlatform.get(platform.getId());
                    if (listGames == null){
                        listGames = new ArrayList<>();
                    }
                    for (int i=0; i < result.length(); i++ ){
                        try {
                            Game game = new Game(result.getJSONObject(i));

                              Optional<Game> optGame = listGames.stream().filter(o -> o.getId() == game.getId()).findAny();
                              if (!optGame.isPresent()){
                                  listGames.add(game);
                                  adapter.addListItem(game, listGames.size());
                              }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mGamesPlatform.put(platform.getId(), listGames);
                    Helper.fetchingGamesData = false;
                    pd.dismiss();
                });
    }

    public List<Game> getListGame(Long idPlatform){
        return mGamesPlatform.get(idPlatform) == null ? new ArrayList<>() : mGamesPlatform.get(idPlatform);
    }

    public void getTextsFromApi(final boolean genre, String ids, final TextView textView ){
        String endpoint = String.format("%s%n/%s%n?fields=name&order=name:asc", genre ? "genres" : "platforms", ids );
        NetworkController.getInstance(mCtx).fetchData(endpoint,
                (JSONArray result) -> {
                    StringBuilder strNames = new StringBuilder();
                    for (int i=0; i < result.length(); i++ ){
                        try {
                            strNames.append( result.getJSONObject(i).getString("name") + ", " );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    textView.setText(String.format("%s: %s", genre ? "Genres: " : "Platforms: " , strNames.toString().substring(0, strNames.toString().length()-2) ) );
                });
    }
}
