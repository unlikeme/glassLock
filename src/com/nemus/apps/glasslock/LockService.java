package com.nemus.apps.glasslock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.nemus.apps.glasslock.ui.LayerView;

/**
 * 
 * 동작중인 키가드의 동작을 활성/비활성 하기위한 서비스입니다.
 * 
 * @author unlikeme
 *
 */
public class LockService extends Service {

    private final static String TAG = "xxx";

    private KeyguardLock mLock;
    private IntentFilter mFilter; 
    private Receiver mReceiver;
    private LayerView mLv;
    private TelephonyManager mTelMgr;
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Log.d(TAG, "LockService - onCreate ");
        
        KeyguardManager manager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        mLock = manager.newKeyguardLock(KEYGUARD_SERVICE);
        mLock.disableKeyguard();

        mTelMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        mReceiver = new Receiver();

        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);
        
        registerReceiver(mReceiver, mFilter);
        
        mLv = new LayerView(this, "topLayer");
        
    }
    
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "LockService - onDestroy ");
        mLock.reenableKeyguard();
        
        unregisterReceiver(mReceiver);
        
    }
    
    
    private class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction();
            
            if(Intent.ACTION_SCREEN_OFF.equals(act)){
                if(Utils.isServiceOn(context)){
                    int state = mTelMgr.getCallState();
                    if(state == TelephonyManager.CALL_STATE_IDLE){
                        startLock(context);
                    }
                }
                
            }
        }
    }
    
    private void startLock(Context ctx){
        if( mLv == null ){
            mLv = new LayerView(this, "topLayer");
        }
        mLv.addLayer();
    }
    
}



