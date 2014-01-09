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

/**
 * 
 * 설정화면 입니다.
 * 
 * @author unlikeme
 *
 */
public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

    private CheckBoxPreference mOnOff;
    private Preference mGestureManage;
    private CheckBoxPreference mGestureVisibility;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        
        mOnOff = (CheckBoxPreference)getPreferenceScreen().findPreference(Const.PREFS_SERVICE_ONOFF);
        mOnOff.setOnPreferenceChangeListener(this);

        mGestureManage = getPreferenceManager().findPreference("gesture_manage");
        mGestureManage.setOnPreferenceClickListener(this);
        
        mGestureVisibility = (CheckBoxPreference) getPreferenceManager().findPreference("gesture_visibility");
        
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
        if(preference.equals(mGestureManage)){
            startActivity(new Intent(SettingsActivity.this, GestureBuilderActivity.class));
            return true;
        }
        return false;
    }
    
    
}



