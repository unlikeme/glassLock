package com.nemus.apps.glasslock;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.nemus.apps.glasslock.lock.gesture.LayerView;

public class Utils {

    private static SharedPreferences mPrefs;
    private static View mLockView;
    private static SharedPreferences getPrefs(Context ctx){
        if( mPrefs == null ){
            mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        }
        return mPrefs;
    }

    public static boolean getBoolean(Context ctx, String key){
        return getPrefs(ctx).getBoolean(key, false);
    }


    public static void setLockView(Context ctx){
        mLockView = new LayerView(ctx);
    }
    public static View getLockView(){
        return mLockView;
    }
    
}
