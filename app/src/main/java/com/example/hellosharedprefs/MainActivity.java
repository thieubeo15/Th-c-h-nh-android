package com.example.hellosharedprefs;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private int mColor;
    private TextView mShowCountTextView;
    private SharedPreferences mPreferences;
    private final String mSharedPrefFile = "com.example.android.hellosharedprefs";

    private static final String COUNT_KEY = "count";
    private static final String COLOR_KEY = "color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mShowCountTextView = findViewById(R.id.textViewCount);
        Button buttonIncrease = findViewById(R.id.buttonIncrease);
        Button buttonReset = findViewById(R.id.buttonReset);
        Button buttonRed = findViewById(R.id.buttonRed);
        Button buttonBlue = findViewById(R.id.buttonBlue);
        Button buttonGreen = findViewById(R.id.buttonGreen);


        mPreferences = getSharedPreferences(mSharedPrefFile, MODE_PRIVATE);
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mColor = mPreferences.getInt(COLOR_KEY, ContextCompat.getColor(this, R.color.white)); // Màu mặc định


        mShowCountTextView.setText(String.valueOf(mCount));
        mShowCountTextView.setBackgroundColor(mColor);


        buttonIncrease.setOnClickListener(view -> {
            mCount++;
            mShowCountTextView.setText(String.valueOf(mCount));
            savePreferences();
        });


        buttonReset.setOnClickListener(view -> reset());

        buttonRed.setOnClickListener(view -> changeBackgroundColor(Color.RED));
        buttonBlue.setOnClickListener(view -> changeBackgroundColor(Color.BLUE));
        buttonGreen.setOnClickListener(view -> changeBackgroundColor(Color.GREEN));
    }


    private void reset() {
        mCount = 0;
        mColor = ContextCompat.getColor(this, R.color.white);
        mShowCountTextView.setText(String.valueOf(mCount));
        mShowCountTextView.setBackgroundColor(mColor);

        // Lưu vào SharedPreferences
        savePreferences();
    }

    private void changeBackgroundColor(int color) {
        mColor = color;
        mShowCountTextView.setBackgroundColor(mColor);
        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_KEY, mCount);
        preferencesEditor.putInt(COLOR_KEY, mColor);
        preferencesEditor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }
}
