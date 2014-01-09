package com.nemus.apps.glasslock;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.nemus.apps.glasslock.lock.OnLockListener;



public class LockFrame extends FrameLayout {

    private static final String TAG = "LockFrame";
    
    private WindowManager.LayoutParams mParam;
    private WindowManager mWindowManager;
    private boolean mIsAdded;
    private View mLockView;
    private LockListener mLockListener = new LockListener() {
        @Override
        public void onSuccess() {
            removeLayer();
        }
        @Override
        public void onFail() {
            Vibrator vibe = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(200);
        }
    };
    
    public LockFrame(Context context, String title) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mParam = new WindowManager.LayoutParams();
        mParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mParam.format = PixelFormat.TRANSLUCENT;
        mParam.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mParam.gravity = Gravity.LEFT | Gravity.TOP;
        mParam.setTitle(title);
        mParam.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParam.height = WindowManager.LayoutParams.MATCH_PARENT;
        
        setFocusable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        
        mIsAdded = false;
        
    }

    void addLayer(){
        if( !mIsAdded ) {
            mLockView = Utils.getLockView();
            ((OnLockListener)mLockView).setOnLockListener(mLockListener);
            addView(mLockView);
            mWindowManager.addView(this, mParam);
            mIsAdded = true;
        }
    }

    void removeLayer() {
        if( mIsAdded ) {
            ((OnLockListener)mLockView).setOnLockListener(null);
            removeAllViews();
            mWindowManager.removeView(this);
            mIsAdded = false;
        }
    }
    
}





