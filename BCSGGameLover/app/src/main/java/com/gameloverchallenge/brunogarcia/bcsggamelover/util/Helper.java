package com.gameloverchallenge.brunogarcia.bcsggamelover.util;

import android.content.Context;
import android.os.Build;

import java.util.List;

public class Helper {


    public static long SECOND = 1000;
    public static long MINUTE = SECOND * 60;
    public static long HOUR = MINUTE * 60;
    public static boolean fetchingPlatformData = false;
    public static boolean fetchingGamesData = false;

    public static String getResourceByName(Context ctx, String name){
        return ctx.getResources().getString(ctx.getResources().getIdentifier(name, "string", ctx.getPackageName()));
    }

    public static int getColor(Context ctx, int resColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return ctx.getResources().getColor(resColor, ctx.getTheme());
        else return ctx.getResources().getColor(resColor);
    }

    public static String listToString(List<?> list ){
        return list.toString().replace("[","").replace("]","").replace(" ","");
    }


}
