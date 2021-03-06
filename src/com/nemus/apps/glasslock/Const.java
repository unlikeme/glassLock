package com.nemus.apps.glasslock;


/**
 * 
 * 상수 및 문자열을 관리하는 인터페이스입니다.
 * 
 * @author unlikeme
 *
 */
public interface Const {
    
    public static final String PKGNAME = "com.nemus.apps.glasslock";
    
    public static final String ACTION_START_SERVICE     = PKGNAME + ".ACTION_START_SERVICE"; 
    public static final String ACTION_STOP_SERVICE      = PKGNAME + ".ACTION_STOP_SERVICE";
    
    
    // Shared Preference Key
    //public static final String SHARED_PREFS_KEY         = PKGNAME + ".prefs";
 
    public static final String PREFS_SERVICE_ONOFF      = "prefs_service_onoff";
    public static final String PREFS_GESTURE_VISIBILTY  = "gesture_visibility";
    public static final String PREFS_AUTO_UNLOCK        = "auto_unlock";
    
}




