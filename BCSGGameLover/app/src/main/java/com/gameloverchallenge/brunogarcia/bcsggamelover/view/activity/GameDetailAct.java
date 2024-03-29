package com.gameloverchallenge.brunogarcia.bcsggamelover.view.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.cache.CacheData;
import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Game;
import com.gameloverchallenge.brunogarcia.bcsggamelover.util.Helper;

public class GameDetailAct extends AppCompatActivity {

    private Game mGame;
    private Toolbar mToolbar;
    private TextView txtGenre, txtPlatform, txtName, txtSummary;
    private LinearLayout llView;
    private SimpleDraweeView imgCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mGame = (Game)getIntent().getExtras().getSerializable("game");
        initializeComponents();
    }

    private void initializeComponents(){

        getSupportActionBar().setTitle(mGame.getName());
        llView = findViewById(R.id.llView);
        int colorTitle = getIntent().getExtras().getInt("colorTitle");
        int colorBack = getIntent().getExtras().getInt("colorBack");
        paintScreen(colorTitle, colorBack);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener( (View v) -> onBackPressed() );
        txtGenre = findViewById(R.id.txtGenre);
        txtName = findViewById(R.id.txtName);
        txtPlatform = findViewById(R.id.txtPlatform);
        txtSummary = findViewById(R.id.txtSummary);
        imgCover = findViewById(R.id.imgCover);

        txtName.setText(mGame.getName());

        txtSummary.setText(mGame.getSummary());
        if (mGame.getCover() != null &&  !mGame.getCover().equals("") ) {
            try {
                GenericDraweeHierarchy hierarchy =
                        GenericDraweeHierarchyBuilder.newInstance(getResources())
                                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                .setFadeDuration(200)
                                .setFailureImage(R.drawable.game)
                                .setFailureImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                .setProgressBarImage(R.drawable.loading)
                                .setProgressBarImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                .build();
                imgCover.setHierarchy(hierarchy);

                Uri uri = Uri.parse("https:" + mGame.getCover().replace("t_thumb", "t_cover_big_2x"));
                imgCover.setImageURI(uri);
            } catch (Exception e){
                e.getStackTrace();
            }
        }


        if (mGame.getGenres().size() > 0) {
             //ids = mGame.getGenres().toString().replace("[","").replace("]","").replace(" ","");
            CacheData.getInstance(getBaseContext()).getTextsFromApi(true, Helper.listToString(mGame.getGenres()), txtGenre);
        } else txtGenre.setText("Genre: -- ");


        if (mGame.getPlatforms().size() > 0) {
            //ids = mGame.getPlatforms().toString().replace("[","").replace("]","").replace(" ","");
            CacheData.getInstance(getBaseContext()).getTextsFromApi(false, Helper.listToString(mGame.getPlatforms()), txtPlatform);
        } else txtPlatform.setText("Platforms: -- ");
    }

    private void paintScreen(int colorTitle, int colorBack){
        mToolbar.setBackgroundColor(colorTitle);
        llView.setBackgroundColor(colorBack);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(colorTitle);
            getWindow().setNavigationBarColor(colorTitle);
        }
    }

}
