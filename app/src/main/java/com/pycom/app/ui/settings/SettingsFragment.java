package com.pycom.app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.pycom.app.R;
import com.pycom.app.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel viewModel;
    private SwitchMaterial darkModeSwitch;
    private SeekBar fontSizeSeekBar;
    private TextView fontSizeValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        
        darkModeSwitch = view.findViewById(R.id.switch_dark_mode);
        fontSizeSeekBar = view.findViewById(R.id.seekbar_font_size);
        fontSizeValue = view.findViewById(R.id.text_font_size_value);
        
        setupObservers();
        setupListeners();
        
        return view;
    }

    private void setupObservers() {
        viewModel.isDarkMode().observe(getViewLifecycleOwner(), isDarkMode -> {
            darkModeSwitch.setChecked(isDarkMode);
        });
        
        viewModel.getFontSize().observe(getViewLifecycleOwner(), fontSize -> {
            fontSizeSeekBar.setProgress(fontSize - 12);
            fontSizeValue.setText(fontSize + "sp");
        });
    }

    private void setupListeners() {
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setDarkMode(isChecked);
            applyTheme(isChecked);
        });
        
        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSize = progress + 12;
                fontSizeValue.setText(fontSize + "sp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int fontSize = seekBar.getProgress() + 12;
                viewModel.setFontSize(fontSize);
            }
        });
    }

    private void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        requireActivity().recreate();
    }
}
