package com.pycom.app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pycom.app.R;
import com.pycom.app.data.local.PreferencesManager;
import com.pycom.app.ui.editor.EditorFragment;
import com.pycom.app.ui.files.FileManagerFragment;
import com.pycom.app.ui.settings.SettingsFragment;
import com.pycom.app.ui.terminal.TerminalFragment;

public class MainActivity extends AppCompatActivity {

    private PreferencesManager preferencesManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        preferencesManager = new PreferencesManager(this);
        applyTheme();
        
        setContentView(R.layout.activity_main);
        
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();
        
        if (savedInstanceState == null) {
            loadFragment(new EditorFragment());
        }
    }

    private void applyTheme() {
        boolean isDarkMode = preferencesManager.isDarkMode();
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_editor) {
                fragment = new EditorFragment();
            } else if (itemId == R.id.nav_terminal) {
                fragment = new TerminalFragment();
            } else if (itemId == R.id.nav_files) {
                fragment = new FileManagerFragment();
            } else if (itemId == R.id.nav_settings) {
                fragment = new SettingsFragment();
            }
            
            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
