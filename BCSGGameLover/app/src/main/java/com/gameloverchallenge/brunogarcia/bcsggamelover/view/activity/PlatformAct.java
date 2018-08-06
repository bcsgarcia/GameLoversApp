package com.gameloverchallenge.brunogarcia.bcsggamelover.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;

import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.cache.CacheData;
import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Platform;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.fragment.PlatformFrg;

import java.util.ArrayList;
import java.util.List;

public class PlatformAct extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PlatformFrgPagerAdapter mSectionsPagerAdapter;

    private final String TAG = "PlatformAct";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private List<PlatformFrg> listPlatformFrg;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ColorDrawable atualColor;

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

        listPlatformFrg = new ArrayList<>();
        int contTab = 0;
        for (Platform platform: CacheData.getInstance(getBaseContext()).getListPlatforms()) {
            listPlatformFrg.add(PlatformFrg.newInstance(getBaseContext(), platform, getColorForTab(contTab, true), getColorForTab(contTab, false)));
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

        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            color = getResources().getColor(R.color.colorPrimaryDark, getTheme());
        else color = getResources().getColor(R.color.colorPrimaryDark);
        mToolbar.setBackgroundColor(color);
        mTabLayout.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
            getWindow().setNavigationBarColor(color);
        }
    }

    private int getColorForTab(int position, boolean cell) {
        switch (position) {
            case 0:
                if (!cell) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.colorPrimaryDark, getTheme());
                    return getResources().getColor(R.color.colorPrimaryDark);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_blue_100, getTheme());
                    return getResources().getColor(R.color.md_blue_100);
                }
            case 1:
                if (!cell){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_brown_800, getTheme());
                    return getResources().getColor(R.color.md_brown_800);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_brown_100, getTheme());
                    return getResources().getColor(R.color.md_brown_100);
                }

            case 2:
                if (!cell) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_red_800, getTheme());
                    return getResources().getColor(R.color.md_red_800);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_red_100, getTheme());
                    return getResources().getColor(R.color.md_red_100);
                }

            case 3:
                if (!cell) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_pink_800, getTheme());
                    return getResources().getColor(R.color.md_pink_800);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_pink_100, getTheme());
                    return getResources().getColor(R.color.md_pink_100);
                }
            case 4:
                if (!cell) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_green_800, getTheme());
                    return getResources().getColor(R.color.md_green_800);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_green_100, getTheme());
                    return getResources().getColor(R.color.md_green_100);
                }
            case 5:
                if (!cell) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_cyan_800, getTheme());
                    return getResources().getColor(R.color.md_cyan_800);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_cyan_100, getTheme());
                    return getResources().getColor(R.color.md_cyan_100);
                }
            case 6:
                if (!cell) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_deep_purple_800, getTheme());
                    return getResources().getColor(R.color.md_deep_purple_800);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        return getResources().getColor(R.color.md_deep_purple_100, getTheme());
                    return getResources().getColor(R.color.md_deep_purple_100);
                }
            default:
                return 0;
        }

    }

    private void changeColor(int colorFrom, int colorTo){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (int) animator.getAnimatedValue();

                mToolbar.setBackgroundColor(color);
                mTabLayout.setBackgroundColor(color);
                //floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(color);
                    getWindow().setNavigationBarColor(color);
                }
            }

        });
        colorAnimation.setDuration(550);
        colorAnimation.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_platform, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PlatformFrgPagerAdapter extends FragmentPagerAdapter {
        //final int PAGE_COUNT = 3;
        //private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
        private Context context;


        public PlatformFrgPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            /*tabTitles = new String[listPlatform.size()];
            for (int i=0; i < listPlatform.size(); i++){
                tabTitles[i] = listPlatform.get(i).getName();
            }*/
        }

        @Override
        public int getCount() {
            return CacheData.getInstance(getBaseContext()).getListPlatforms().size();
        }

        @Override
        public Fragment getItem(int position) {
            return listPlatformFrg.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            Log.d(TAG, CacheData.getInstance(getBaseContext()).getListPlatforms().get(position).getName());
            return CacheData.getInstance(getBaseContext()).getListPlatforms().get(position).getName();
        }
    }
}
