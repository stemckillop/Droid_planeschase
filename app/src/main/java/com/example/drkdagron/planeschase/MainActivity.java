package com.example.drkdagron.planeschase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by stemc on 2017-03-06.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openPlanes(View v)
    {
        Intent i = new Intent(this, PlanesActivity.class);
        startActivity(i);
    }

    public void openArch(View v)
    {
        Intent i = new Intent(this, SchemeActivity.class);
        startActivity(i);
    }

}
