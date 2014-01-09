package com.nemus.apps.glasslock.ui;

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
import com.nemus.apps.glasslock.lock.gesture.GestureBuilderActivity;
import com.nemus.apps.glasslock.lock.gesture.GestureSettingsActivity;

/**
 * 
 * 설정화면 입니다.
 * 
 * @author unlikeme
 *
 */
public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

    private CheckBoxPreference mOnOff;
    private Preference mGesture;
    private Preference mKnock;
    private CheckBoxPreference mAutoUnlock;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        
        mOnOff = (CheckBoxPreference)getPreferenceScreen().findPreference(Const.PREFS_SERVICE_ONOFF);
        mOnOff.setOnPreferenceChangeListener(this);

        mGesture = getPreferenceManager().findPreference("settings_gesture");
        mGesture.setOnPreferenceClickListener(this);
        
        mKnock = getPreferenceManager().findPreference("settings_knock");
        mKnock.setOnPreferenceClickListener(this);

        mAutoUnlock = (CheckBoxPreference) getPreferenceManager().findPreference(Const.PREFS_AUTO_UNLOCK);
        
/*        
        getSharedPreferences(Const.SHARED_PREFS_KEY, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
        
        Button btnGesture = (Button)findViewById(R.id.btnGesture);
        btnGesture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, GestureBuilderActivity.class));
            }
        });
*/
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        // TODO Auto-generated method stub
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if( preference.equals(mOnOff) ){
            boolean isOn = (Boolean) newValue;
            if( isOn ){
                sendBroadcast(new Intent(Const.ACTION_START_SERVICE));
            }else{
                sendBroadcast(new Intent(Const.ACTION_STOP_SERVICE));
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference.equals(mGesture)){
            startActivity(new Intent(this, GestureSettingsActivity.class));
            return true;
        }
        return false;
    }
    
    
}



