package com.gameloverchallenge.brunogarcia.bcsggamelover.view.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.cache.CacheData;
import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Platform;
import com.gameloverchallenge.brunogarcia.bcsggamelover.util.Helper;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.fragment.PlatformFrg;

import java.util.ArrayList;
import java.util.List;

public class PlatformAct extends AppCompatActivity {

    private final String TAG = "PlatformAct";
    private ViewPager mViewPager;
    private List<PlatformFrg> mListPlatformFrg;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);
        initializeComponents();
    }

    private void initializeComponents(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Game Lovers");

        mListPlatformFrg = new ArrayList<>();
        int contTab = 0;
        for (Platform platform: CacheData.getInstance(getBaseContext()).getListPlatforms()) {
            mListPlatformFrg.add(PlatformFrg.newInstance(getBaseContext(), platform, getColorForTab(contTab, true), getColorForTab(contTab, false)));
            contTab++;
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PlatformFrgPagerAdapter(getSupportFragmentManager(), PlatformAct.this));

        // Give the TabLayout the ViewPager
        mTabLayout = findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                int colorFrom = ((ColorDrawable) mToolbar.getBackground()).getColor();
                int colorTo =getColorForTab(tab.getPosition(), false);
                changeColor(colorFrom,colorTo);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setTabTextColors(getResources().getColor(R.color.md_blue_grey_300), getResources().getColor(R.color.md_white_1000));



        changeColor(((ColorDrawable) mToolbar.getBackground()).getColor(),getColorForTab(0, false));
    }

    private int getColorForTab(int position, boolean cell) {
        switch (position) {
            case 0:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.colorPrimaryDark);
                else return Helper.getColor(getBaseContext(), R.color.md_blue_100);
            case 1:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.md_brown_800);
                else return Helper.getColor(getBaseContext(), R.color.md_brown_100);
            case 2:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.md_red_800);
                else return Helper.getColor(getBaseContext(), R.color.md_red_100);
            case 3:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.md_pink_800);
                else return Helper.getColor(getBaseContext(), R.color.md_pink_100);
            case 4:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.md_green_800);
                 else return Helper.getColor(getBaseContext(), R.color.md_green_100);
            case 5:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.md_cyan_800);
                else return Helper.getColor(getBaseContext(), R.color.md_cyan_100);
            case 6:
                if (!cell) return Helper.getColor(getBaseContext(), R.color.md_deep_purple_800);
                else return Helper.getColor(getBaseContext(), R.color.md_deep_purple_100);
            default:
                return 0;
        }

    }

    private void paintScreen(int color){
        mToolbar.setBackgroundColor(color);
        mTabLayout.setBackgroundColor(color);
        //floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
            getWindow().setNavigationBarColor(color);
        }
    }

    private void changeColor(int colorFrom, int colorTo){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener((ValueAnimator animator) -> paintScreen((int) animator.getAnimatedValue()) );
        colorAnimation.setDuration(550);
        colorAnimation.start();
    }

    public class PlatformFrgPagerAdapter extends FragmentPagerAdapter {

        private Context context;

        public PlatformFrgPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() { return CacheData.getInstance(getBaseContext()).getListPlatforms().size(); }

        @Override
        public Fragment getItem(int position) { return mListPlatformFrg.get(position); }

        @Override
        public CharSequence getPageTitle(int position) { return CacheData.getInstance(getBaseContext()).getListPlatforms().get(position).getName(); }
    }
}
