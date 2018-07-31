package com.example.android.jokeandroidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private static final String JOKE_KEY = "joke_key";

    private TextView textViewJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        textViewJoke = findViewById(R.id.tv_joke);

        Intent intentCalled = getIntent();
        if (intentCalled != null && intentCalled.hasExtra(JOKE_KEY)) {
            String jokeText = intentCalled.getStringExtra(JOKE_KEY);
            textViewJoke.setText(jokeText);
        }

    }
}
