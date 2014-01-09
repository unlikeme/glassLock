package com.nemus.apps.glasslock.lock.gesture;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.nemus.apps.glasslock.Const;
import com.nemus.apps.glasslock.R;

/**
 * 
 * 설정화면 입니다.
 * 
 * @author unlikeme
 *
 */
public class GestureSettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

    private Preference mGestureManage;
    private CheckBoxPreference mGestureVisibility;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_gesture);
        
        mGestureManage = getPreferenceManager().findPreference("gesture_manage");
        mGestureManage.setOnPreferenceClickListener(this);
        mGestureVisibility = (CheckBoxPreference) getPreferenceManager().findPreference(Const.PREFS_GESTURE_VISIBILTY);
        
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        // TODO Auto-generated method stub
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference.equals(mGestureManage)){
            startActivity(new Intent(this, GestureBuilderActivity.class));
            return true;
        }
        return false;
    }
    
    
}



