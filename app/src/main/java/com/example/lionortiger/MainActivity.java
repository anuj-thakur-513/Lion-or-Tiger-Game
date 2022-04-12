package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Creating an enum for keeping the track of player who is playing
    enum Player {
        ONE, TWO, NO
    }

    // Creating a player variable in order to keep track of players
    Player currentPlayer = Player.ONE;
    // Creating an array of type Player in order to keep track of moves of player choices
    Player[] playerChoices = new Player[9];
    // Creating an array of possibilities in which the player wins
    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;

    AlphaAnimation fadeOut = new AlphaAnimation(0.0f, 1.0f);

    private TextView titleTxtView;
    private Button btnReset;
    private GridLayout mGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fadeOut.setDuration(1000);

        for(int index = 0; index < 9; index++){
            playerChoices[index] = Player.NO;
        }

        titleTxtView = findViewById(R.id.title_txt_view);
        mGridLayout = findViewById(R.id.gridLayout);
        btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;

        // Entering the player value at the pressed index
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(playerChoices[tiTag] == Player.NO && !gameOver) {

            // Getting the image out of the screen so we can add the animation in which it comes out of the screen
            tappedImageView.setTranslationX(-2000);
            tappedImageView.setBackgroundColor(Color.parseColor("#80ffffff"));

            playerChoices[tiTag] = currentPlayer;

            // Setting the image in the ImageView
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }

            // Setting up the animation
            tappedImageView.animate().translationXBy(2000).alpha(1).rotationY(3600).setDuration(500);

            for (int[] winnerColumns : winnerRowsColumns) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] &&
                        playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] &&
                        playerChoices[winnerColumns[0]] != Player.NO) {

                    gameOver = true;
                    btnReset.setVisibility(View.VISIBLE);
                    btnReset.setAnimation(fadeOut);

                    if (currentPlayer == Player.ONE) {
                        titleTxtView.startAnimation(fadeOut);
                        titleTxtView.setText(R.string.tiger_wins);
                    } else if (currentPlayer == Player.TWO) {
                        titleTxtView.startAnimation(fadeOut);
                        titleTxtView.setText(R.string.lion_wins);
                    }
                }
            }
        }
    }

    // Method to reset the game
    private void resetGame(){
        int index = 0;
        while (index < mGridLayout.getChildCount()) {

            ImageView imageView = (ImageView) mGridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setBackgroundColor(Color.BLACK);
            imageView.setAlpha(0.2f);

            index++;
        }

        currentPlayer = Player.ONE;

        for(int i = 0; i < 9; i++){
            playerChoices[i] = Player.NO;
        }
        
        gameOver = false;

        titleTxtView.setText(R.string.lion_or_tiger);

        btnReset.setVisibility(View.GONE);
    }
}