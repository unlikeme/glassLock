package com.nemus.apps.glasslock.lock.gesture;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.util.Log;
import android.view.MotionEvent;

import com.nemus.apps.glasslock.Const;
import com.nemus.apps.glasslock.LockListener;
import com.nemus.apps.glasslock.Utils;
import com.nemus.apps.glasslock.lock.OnLockListener;

import java.util.ArrayList;



public class LayerView extends GestureOverlayView implements OnLockListener{

    private static final String TAG = "xxx";
//    private static final float LENGTH_THRESHOLD = 120.0f;
    private static final float MIN_PREDICTION_SCORE = 5.0f;
    
    private GestureOverlayView mGov;
    private GestureLibrary mStore;
    private Gesture mGesture;
    private LockListener mLockListener;
    
    public LayerView(Context context) {
        super(context);
        
        setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        initGestureAttr();
        
        mStore = GestureLibraries.fromPrivateFile(context, "store");
        addOnGestureListener(new GesturesProcessor());
        
    }

    private void initGestureAttr(){
        if( mGov != null ) {
            boolean b = Utils.getBoolean(getContext(), Const.PREFS_GESTURE_VISIBILTY);
            Log.d(TAG, "visibility : "+b);
            mGov.setGestureVisible(b);
        }
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
                        if(mLockListener != null){
                            mLockListener.onSuccess();
                        }
                    }else{
                        mLockListener.onFail();
                    }
                }else{
                    mLockListener.onSuccess();
                }
            }
        }

        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
        }
    }

    @Override
    public void setOnLockListener(LockListener l) {
        mLockListener = l;
    }
    
}


