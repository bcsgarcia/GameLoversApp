package com.gameloverchallenge.brunogarcia.bcsggamelover.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.entity.Game;
import com.gameloverchallenge.brunogarcia.bcsggamelover.view.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;
import java.util.Random;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {


    private Context mContext;
    private List<Game> mListGame;
    private LayoutInflater mLayoutInflater;
    private String TAG = "GamesAdapter";
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private int mColor;

    public GamesAdapter(Context ctx, List<Game> listGame, int color) {
        mContext = ctx;
        mLayoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListGame = listGame;
        mColor = color;
    }

    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_list_game, parent, false);
        GamesViewHolder mvh = new GamesViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(GamesViewHolder viewHolder, int position) {
        viewHolder.txtName.setText(mListGame.get(position).getName());
        if (mListGame.get(position).getCover() != null &&  !mListGame.get(position).getCover().equals("") ) {
            try {
                GenericDraweeHierarchy hierarchy =
                        GenericDraweeHierarchyBuilder.newInstance(mContext.getResources())
                                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                .setFadeDuration(200)
                                .setFailureImage(R.drawable.game)
                                .setFailureImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                .setProgressBarImage(R.drawable.loading)
                                .setProgressBarImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                                .build();
                viewHolder.imgCover.setHierarchy(hierarchy);

                Uri uri = Uri.parse("https:" + mListGame.get(position).getCover().replace("t_thumb", "t_cover_big_2x"));
                viewHolder.imgCover.setImageURI(uri);
            } catch (Exception e){
                e.getStackTrace();
            }

        }

        viewHolder.rlCard.setBackgroundColor(mColor);

        try {
            YoYo.with(Techniques.Landing).duration(700).playOn(viewHolder.itemView);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListGame.size();
    }

    public void addListItem(Game item, int position) {
        mListGame.add(item);
        notifyItemInserted(position);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){ mRecyclerViewOnClickListenerHack = r; }

    public class GamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View mView;
        public Context mContext;
        public ConstraintLayout rlCard;
        public TextView txtName;
        public SimpleDraweeView imgCover;

        public GamesViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgCover = itemView.findViewById(R.id.imgCover);
            rlCard = itemView.findViewById(R.id.rlCard);
            mView = itemView;
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(view, getLayoutPosition());
            }
        }
    }
}