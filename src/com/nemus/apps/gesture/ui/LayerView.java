package com.nemus.apps.gesture.ui;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;

import com.nemus.apps.gesture.DownLoader;
import com.nemus.apps.gesture.Utils;

import java.util.ArrayList;



public class LayerView extends View {

    private static final String TAG = "xxx";
//    private static final float LENGTH_THRESHOLD = 120.0f;
    private static final float MIN_PREDICTION_SCORE = 5.0f;
    
    private WindowManager.LayoutParams mParam = new WindowManager.LayoutParams();
    private WindowManager mWindowManager;
    private FrameLayout mFl;
    private boolean mIsAdded;
    private GestureOverlayView mGov;
    private GestureLibrary mStore;
    private Gesture mGesture;
    
    public LayerView(Context context, String title){
        this(context, WindowManager.LayoutParams.TYPE_SYSTEM_ERROR, title);
    }
    
    public LayerView(Context context, int type, String title) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mParam.type = type;
        mParam.format = PixelFormat.TRANSLUCENT;
        mParam.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mParam.gravity = Gravity.LEFT | Gravity.TOP;
        mParam.setTitle(title);
        mParam.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParam.height = WindowManager.LayoutParams.MATCH_PARENT;
        
        setFocusable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        
        mFl = new FrameLayout(context);
        mFl.addView(this);
        
        mGov = new GestureOverlayView(context);
        mGov.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        mFl.addView(mGov);
        initGestureAttr();
        mIsAdded = false;
        
        mStore = GestureLibraries.fromPrivateFile(context, "store");
        mGov.addOnGestureListener(new GesturesProcessor());
        
        
    }

    public void succeeded(){
        Log.d("xxx", "successed");
    }
    public void failed(){
        Log.d("xxx", "failed");
    }
    public void completed(){
        Log.d("xxx", "completed");
    }
    public void canceled(){
        Log.d("xxx", "canceled");
    }
    
    private void initGestureAttr(){
        if( mGov != null ) {
            boolean b = Utils.isGestureVisibility(getContext());
            Log.d(TAG, "visibility : "+b);
            mGov.setGestureVisible(b);
        }
    }
    
    
    
    public void addLayer(){
        if( !mIsAdded ) {
            initGestureAttr();
            mWindowManager.addView(mFl, mParam);
            mIsAdded = true;
        }
/*        
        DownLoader loader = new DownLoader(getContext(), this);
        loader.addItem(this, "http://oklinux.net/img/1.jpg");
        loader.addItem(this, "http://oklinux.net/img/2.jpg");
        loader.addItem(this, "http://oklinux.net/img/3.jpg");
        loader.addItem(this, "http://oklinux.net/img/4.jpg");
        loader.start();
*/        
    }

    private void removeLayer() {
        if( mIsAdded ) {
            mWindowManager.removeView(mFl);
            mIsAdded = false;
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("xxx", "keyCode .. : "+keyCode);
        if( keyCode == KeyEvent.KEYCODE_BACK ) {
            //removeLayer();
        }
        return super.onKeyDown(keyCode, event);
    }
    
    private class GesturesProcessor implements GestureOverlayView.OnGestureListener {
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mGesture = null;
        }

        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
        }

        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            mGesture = overlay.getGesture();
            overlay.clear(false);

            if( mStore != null ) {
                mStore.load();
                
                ArrayList<Prediction> predictions = mStore.recognize(mGesture);
                if (!predictions.isEmpty()) {
                    Prediction bestPrediction = predictions.get(0);
                    Log.d("xxx", "name : "+bestPrediction.name+" score : "+bestPrediction.score);
                    if (bestPrediction.score >= MIN_PREDICTION_SCORE) {
                        removeLayer();
                    }else{
                        fail();
                    }
                }else{
                    removeLayer();
                }
            }
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }

    private void fail(){
        Vibrator vibe = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(200);
    }
    
}


