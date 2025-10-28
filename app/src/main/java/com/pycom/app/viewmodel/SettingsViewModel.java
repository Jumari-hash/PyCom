package com.pycom.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pycom.app.data.local.PreferencesManager;

public class SettingsViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> darkMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> fontSize = new MutableLiveData<>();
    private final PreferencesManager preferencesManager;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        this.preferencesManager = new PreferencesManager(application.getApplicationContext());
        
        darkMode.setValue(preferencesManager.isDarkMode());
        fontSize.setValue(preferencesManager.getFontSize());
    }

    public LiveData<Boolean> isDarkMode() {
        return darkMode;
    }

    public LiveData<Integer> getFontSize() {
        return fontSize;
    }

    public void setDarkMode(boolean enabled) {
        preferencesManager.setDarkMode(enabled);
        darkMode.setValue(enabled);
    }

    public void setFontSize(int size) {
        preferencesManager.setFontSize(size);
        fontSize.setValue(size);
    }
}
