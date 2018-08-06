package com.gameloverchallenge.brunogarcia.bcsggamelover.util;

import android.content.Context;

public class Helper {


    public static long SECOND = 1000;
    public static long MINUTE = SECOND * 60;
    public static long HOUR = MINUTE * 60;

    public static boolean fetchingPlatformData = false;
    public static boolean fetchingGamesData = false;

    public static String getResourceByName(Context ctx, String name){
        return ctx.getResources().getString(ctx.getResources().getIdentifier(name, "string", ctx.getPackageName()));
    }


}
