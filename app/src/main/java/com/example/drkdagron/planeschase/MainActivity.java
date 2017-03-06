package com.example.drkdagron.planeschase;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[] original;
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
            case R.id.menu_btnundoplanes:
            {
                undoPlaneswalk();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planeImage = (ImageView)findViewById(R.id.img_plane);

        rnd = new Random();
        cards = new int[42];
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
            final TypedArray imgs = getResources().obtainTypedArray(R.array.planes);
            planeImage.setImageResource(imgs.getResourceId(cards[currentCard],0));
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
        final TypedArray imgs = getResources().obtainTypedArray(R.array.planes);
        planeImage.setImageResource(imgs.getResourceId(cards[currentCard],0));
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
        if (currentCard > 42)
        {
            shuffleDeck();
            Log.e("shuffle", "shuffle");
        }
        else {
            Log.e("Card", String.valueOf(cards[currentCard]));
            final TypedArray imgs = getResources().obtainTypedArray(R.array.planes);
            planeImage.setImageResource(imgs.getResourceId(cards[currentCard], 0));
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

        Toast.makeText(getBaseContext(), "Resuming game", Toast.LENGTH_SHORT).show();


    }
}
