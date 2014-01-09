package com.nemus.apps.glasslock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



/**
 * 실행을 관리하는 리시버 클래스입니다.
 * 
 * @author unlikeme
 *
 */
public class CmdReceiver extends BroadcastReceiver {

    private static final String TAG = "xxx";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        final String act = intent.getAction();
        Log.d(TAG, "act : "+act);
        if(Const.ACTION_START_SERVICE.equals(act)){
            startService(context);
        }else if(Const.ACTION_STOP_SERVICE.equals(act)){
            stopService(context);
        }else if(Intent.ACTION_BOOT_COMPLETED.equals(act)){
            if(Utils.isServiceOn(context)){
                startService(context);
            }
        }else if(Intent.ACTION_USER_PRESENT.equals(act)){
            if(Utils.isServiceOn(context)){
                stopService(context);
                startService(context);
            }
        }
    }

    
    private void startService(Context ctx){
        ctx.startService(new Intent(ctx, LockService.class));
    }
    
    private void stopService(Context ctx){
        ctx.stopService(new Intent(ctx, LockService.class));
    }
    
}



