package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    int eventCounter = 0;
    int activePlayer = 1;
    int[] gamestate = {0,0,0,0,0,0,0,0,0};
    boolean gameActive = true;
    int[][] winningposition={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playMusic(R.raw.background_music);

    }
    public static MediaPlayer music;
    public void playMusic(int id)
    {
        music = MediaPlayer.create(MainActivity.this, id);
        music.setLooping(true);
        music.start();
    }
    public void dropIn(View v)
    {
        ImageView counter = (ImageView) findViewById(v.getId());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gamestate[tappedCounter] == 0 && gameActive)
        {
            eventCounter++;
            gamestate[tappedCounter] = activePlayer;
            if(activePlayer == 1)
            {
                counter.setImageResource(R.drawable.red);
                activePlayer=2;
            }
            else
            {
                counter.setImageResource(R.drawable.yellow);
                activePlayer=1;
            }
            for(int i=0; i<8; i++)
            {
                if(gamestate[winningposition[i][0]] == gamestate[winningposition[i][1]] && gamestate[winningposition[i][1]] == gamestate[winningposition[i][2]] && gamestate[winningposition[i][0]] != 0)
                {
                    gameActive=false;
                    String winner_detail="";
                    if(activePlayer == 2)
                        winner_detail="Red won";
                    else
                        winner_detail="Yellow won";
                    TextView display = findViewById(R.id.display);
                    display.setText(winner_detail);
                    Button button = findViewById(R.id.restart_button);
                    button.setVisibility(View.VISIBLE);
                }
            }
            if(eventCounter == 9 && gameActive)
            {
                gameActive = false;
                TextView display = findViewById(R.id.display);
                display.setText("Try Again");
                Button button = findViewById(R.id.restart_button);
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    public void restart(View v)
    {
        TextView display = findViewById(R.id.display);
        display.setText("");
        Button restart = findViewById(R.id.restart_button);
        restart.setVisibility(View.INVISIBLE);
        int i;
        for(i=0;i<=8;i++)
            gamestate[i]=0;
        activePlayer=1;
        eventCounter=0;
        gameActive=true;
        GridLayout board_grid = findViewById(R.id.board_grid);
        for(i=0;i<=8;i++)
        {
            ImageView image = (ImageView) board_grid.getChildAt(i);
            image.setImageDrawable(null);
        }
    }
}
//    MediaPlayer media;
//    public void tapSound()
//    {
//        media= MediaPlayer.create(getApplicationContext(), R.raw.tap_sound);
//        media.start();
//        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                mp.reset();
//                mp.release();
//                media = null;
//                music.start();
//            };
//        });
//    }