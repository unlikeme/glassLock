package com.nemus.apps.glasslock;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class Utils {

    private static SharedPreferences getPrefs(Context ctx){
        //return ctx.getSharedPreferences(Const.SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static boolean isServiceOn(Context ctx){
        SharedPreferences prefs = getPrefs(ctx);
        boolean b = prefs.getBoolean(Const.PREFS_SERVICE_ONOFF, false);
        Log.d("xxx", "service on : "+b);
        return b;
    }
    
    public static boolean isGestureVisibility(Context ctx){
        SharedPreferences prefs = getPrefs(ctx);
        boolean b = prefs.getBoolean("gesture_visibility", false);
        Log.d("xxx", "visibility : "+b);
        return b;
    }
    
    
/*    
    public static void setServiceOn(Context ctx, boolean b){
        SharedPreferences prefs = getPrefs(ctx);
        Editor ed = prefs.edit();
        ed.putBoolean(Const.PREFS_SERVICE_ONOFF, b);
        ed.commit();
    }
*/    
    
}
