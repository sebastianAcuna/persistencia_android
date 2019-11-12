package com.example.persistenciaenandroid.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.persistenciaenandroid.R;

public class CuentaFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config_preference);
    }
}
