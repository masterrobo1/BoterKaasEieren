package com.rocdev.android.boterkaaseieren;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int playerActive;
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int [][] winners = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6},

    };
    boolean gameFinished;
    int playerWinner;
    TextView endGameTextView;
    Button newGameButton;
    LinearLayout endGameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        playerActive = 1;
        playerWinner = 0;
        endGameTextView = (TextView) findViewById(R.id.endGameTextView);
        newGameButton = (Button) findViewById(R.id.newGameButton);
        endGameLayout = (LinearLayout) findViewById(R.id.endGameLayout);
    }

    public void setImg(View view){
        ImageView imageView = (ImageView) view;
        int index = Integer.parseInt (imageView.getTag().toString());

        if (gameState[index] == 0) {
            gameState[index] = playerActive;
            if (playerActive == 1) {
                imageView.setTranslationY(-1000f);
                imageView.setImageResource(R.drawable.kruisje);
                imageView.animate().translationY(0f).setDuration(300);
                checkEndGame();
                playerActive = 2;
            } else {
                imageView.setTranslationY(-1000f);
                imageView.setImageResource(R.drawable.rondje);
                imageView.animate().translationY(0f).setDuration(300);
                checkEndGame();
                playerActive = 1;
            }

            if(gameFinished){
                endGame();


            }
        }
    }

    private void checkEndGame(){
        for(int[] winner: winners) {
            boolean isWinner = true;
            for (int i: winner){
                if (gameState[i] != playerActive){
                    isWinner = false;
                    break;
                }
            }
            if(isWinner){
                playerWinner = playerActive;
                gameFinished = true;
            }
        }

        //gelijkspel situatie

        if (playerWinner == 0){
            gameFinished = true;
            for(int i: gameState) {
                if (i == 0) {
                    gameFinished = false;
                    break;
                }
            }
        }
    }

    private void endGame(){
        switch (playerWinner){
            case 0:
                endGameTextView.setText("Gelijk spel, opnieuw spelen?");
                break;
            case 1:
                endGameTextView.setText("Speler 1 heeft gewonnen, opnieuw spelen?");
                break;
            case 2:
                endGameTextView.setText("Speler 2 heeft gewonnen, opnieuw spelen?");
                break;
        }
        endGameLayout.setVisibility(View.VISIBLE);
    }

    public void nieuwSpel(View view){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 0;
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageResource(0);
        }
        playerActive = 1;
        gameFinished = false;
        playerWinner = 0;
        endGameLayout.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
