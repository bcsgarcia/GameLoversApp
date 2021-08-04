package com.gameloverchallenge.brunogarcia.bcsggamelover.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.cache.CacheData;
import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Platform;
import com.gameloverchallenge.brunogarcia.bcsggamelover.util.Helper;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.interfaces.RecyclerViewOnClickListenerHack;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.activity.GameDetailAct;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.adapter.GamesAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlatformFrg extends Fragment implements RecyclerViewOnClickListenerHack {

    public static final String ARG_PLAT = "ARG_PLAT";
    public static final String ARG_COLOR = "ARG_COLOR";
    public static final String ARG_COLOR_PAGE = "ARG_COLOR_PAGE";
    private final String TAG = "PlatformFrg";
    private View mRootView;
    private RecyclerView mRvListGames;
    private GamesAdapter mAdapter;
    private GridLayoutManager mGLM;
    private ProgressDialog mProgress;
    private Platform mPlatform;
    private int mColorCell;
    private int mColorPage;
    private int mIdxConsulta = 0;

    public static PlatformFrg newInstance(Context ctx, Platform platform, int colorCell, int colorPage) {
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, colorCell);
        args.putInt(ARG_COLOR_PAGE, colorPage);
        args.putSerializable(ARG_PLAT, platform);
        PlatformFrg fragment = new PlatformFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlatform = (Platform) getArguments().getSerializable(ARG_PLAT);
        mColorCell = getArguments().getInt(ARG_COLOR);
        mColorPage = getArguments().getInt(ARG_COLOR_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_platform, container, false);
        initializeComponents();
        return mRootView;
    }

    private void initializeComponents(){
        mGLM = new GridLayoutManager(getActivity(), 2);
        mRvListGames = mRootView.findViewById(R.id.rvListGames);
        mRvListGames.setLayoutManager(mGLM);

        mRvListGames.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (CacheData.getInstance(getContext()).getListGame(mPlatform.getId()).size() == mGLM.findLastCompletelyVisibleItemPosition() + 1) {
                    getNextGames();
                }
            }
        });

        mRvListGames.setHasFixedSize(true);
        mAdapter = new GamesAdapter(getActivity(), CacheData.getInstance(getContext()).getListGame(mPlatform.getId()), mColorCell);
        mAdapter.setRecyclerViewOnClickListenerHack(this);
        mRvListGames.setAdapter(mAdapter);

        if (mIdxConsulta == 0){
            getNextGames();
        }

    }

    @Override
    public void onClickListener(View view, int position) {
        Intent it = new Intent(getActivity(), GameDetailAct.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("game", CacheData.getInstance(getContext()).getListGame(mPlatform.getId()).get(position));
        mBundle.putInt("colorTitle", mColorPage);
        mBundle.putInt("colorBack", mColorCell);
        it.putExtras(mBundle);
        startActivity(it);

    }

    private void getNextGames(){
        try {
            mIdxConsulta+= 19;
            List<Long> listId = new ArrayList<>();
            for (int i = CacheData.getInstance(getContext()).getListGame(mPlatform.getId()).size(); i <= mIdxConsulta;  i++  ){
                try {
                    listId.add(mPlatform.getGames().get(i));
                } catch (Exception e){ }
            }

            mProgress = new ProgressDialog(getActivity());
            mProgress.setMessage("Aguarde...");
            mProgress.show();

            GamesAdapter adapter = (GamesAdapter) mRvListGames.getAdapter();
            CacheData.getInstance(getContext()).populateGames(Helper.listToString(listId), mPlatform, adapter, mProgress);
        } catch (Exception e){
            e.getStackTrace();
        }
    }

}
