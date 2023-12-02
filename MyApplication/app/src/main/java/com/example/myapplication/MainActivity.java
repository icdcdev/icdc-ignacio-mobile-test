package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.util.DatePickerFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(view -> {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.logged_key), false);
            editor.apply();

            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        });

        binding.calendarImage.setOnClickListener(v->{
            showDatePickerDialog();
        });
        binding.fechaLabel.setOnClickListener(v->{
            showDatePickerDialog();
        });

        setupTabs();
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void setupTabs() {
        TabLayout tabLayout = binding.tabLayout;
        TabLayout.Tab tabAsignados = tabLayout.newTab();
        TabLayout.Tab tabNoAsignados = tabLayout.newTab();
        tabAsignados.setText("Asignados");
        tabNoAsignados.setText("No Asignados");
        //tabAsignados.setIcon(R.drawable.ic_launcher);
        tabLayout.addTab(tabAsignados);
        tabLayout.addTab(tabNoAsignados, true);
    }
}