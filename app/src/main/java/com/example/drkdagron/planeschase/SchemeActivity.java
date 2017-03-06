package com.example.drkdagron.planeschase;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by stemc on 2017-03-06.
 */

public class SchemeActivity extends AppCompatActivity {

    int[] schemes;
    Random rnd;
    int currentCard = 0;

    ImageView schemeView;
    LinearLayout schemeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);

        schemes = new int[47];
        schemeView = (ImageView)findViewById(R.id.img_scheme);
        schemeView.setOnTouchListener(new OnSwipeTouchListener(SchemeActivity.this) {
            public void onSwipeRight() {
                undoScheme();
            }
            public void onSwipeLeft() {

                NextScheme(getCurrentFocus());
            }
        });

        rnd = new Random();
        shuffleDeck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainactivity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_btnnewgame:
            {
                shuffleDeck();
                return true;
            }
            case R.id.menu_btnundo:
            {
                undoScheme();
                return true;
            }
            case R.id.menu_btnback:
            {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void NextScheme(View v)
    {
        Log.e("TEST", String.valueOf(currentCard));
        currentCard++;
        if (currentCard >= 47)
        {
            shuffleDeck();
        }
        else {
            getImage();
        }
    }

    private void undoScheme()
    {
        Log.e("BACK", "BACK");
        if (currentCard == 0)
        {
            return;
        }
        else
        {
            currentCard--;
            getImage();
        }
    }

    private void shuffleDeck()
    {
        for (int i= 0; i < schemes.length; i++)
        {
            schemes[i] = i + 1;
        }

        int tmp;
        for (int i= 0; i < schemes.length; i++)
        {
            int newp = rnd.nextInt(schemes.length);
            tmp = schemes[i];
            schemes[i] = schemes[newp];
            schemes[newp] = tmp;
        }

        currentCard = 0;
        getImage();
    }

    private void getImage()
    {
        final TypedArray imgs = getResources().obtainTypedArray(R.array.schemes);
        schemeView.setImageResource(imgs.getResourceId(schemes[currentCard] - 1, 0));
    }
}
