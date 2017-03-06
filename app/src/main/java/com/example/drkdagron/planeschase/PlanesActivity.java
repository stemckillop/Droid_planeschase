package com.example.drkdagron.planeschase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class PlanesActivity extends AppCompatActivity {

    int[] cards;
    int currentCard = 0;

    Random rnd;
    ImageView planeImage;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainactivity, menu);
        return true;
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
                undoPlaneswalk();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes);

        planeImage = (ImageView)findViewById(R.id.img_plane);
        planeImage.setOnTouchListener(new OnSwipeTouchListener(PlanesActivity.this) {
            public void onSwipeRight() {
                undoPlaneswalk();
            }
            public void onSwipeLeft() {

                Planeswalk(getCurrentFocus());
            }
        });

        rnd = new Random();
        cards = new int[83];
        for (int i= 0; i < cards.length; i++)
        {
            cards[i] = i + 1;
        }

        shuffleDeck();
    }

    private void undoPlaneswalk()
    {
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
        for (int i= 0; i < cards.length; i++)
        {
            cards[i] = i + 1;
        }

        int tmp;
        for (int i= 0; i < cards.length; i++)
        {
            int newp = rnd.nextInt(cards.length);
            tmp = cards[i];
            cards[i] = cards[newp];
            cards[newp] = tmp;
        }

        currentCard = 0;
        getImage();
    }

    public void RollDice(View v)
    {
        int roll = rnd.nextInt(6);
        if (roll == 0)
        {
            Toast.makeText(getBaseContext(), R.string.chaos, Toast.LENGTH_SHORT).show();
        }
        else if (roll == 5)
        {
            Toast.makeText(getBaseContext(), R.string.planewalk, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getBaseContext(), R.string.nothing, Toast.LENGTH_SHORT).show();
        }
    }
    public void Planeswalk(View v)
    {
        Log.e("TEST", String.valueOf(currentCard));
        currentCard++;
        if (currentCard >= 83)
        {
            shuffleDeck();
            Log.e("shuffle", "shuffle");
        }
        else {
            Log.e("Card", String.valueOf(cards[currentCard]));
            getImage();
            Log.e("next card", "next card");
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        SharedPreferences prefs = this.getSharedPreferences("com.drkdagron.planeschase", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        for (int i= 0; i < cards.length; i++)
        {
            edit.putInt("order"+String.valueOf(i), cards[i]);
        }
        edit.putInt("current", currentCard);
        edit.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("com.drkdagron.planeschase", Context.MODE_PRIVATE);

        for (int i= 0; i < cards.length; i++)
        {
            cards[i] = prefs.getInt("order"+String.valueOf(i), i);
        }
        currentCard = prefs.getInt("current", 0);
        getImage();
    }

    private void getImage()
    {
        Log.e("getImage", String.valueOf(currentCard) + " " + String.valueOf(cards[currentCard]));
        final TypedArray imgs = getResources().obtainTypedArray(R.array.planes);
        planeImage.setImageResource(imgs.getResourceId(cards[currentCard] - 1, 0));
    }
}
