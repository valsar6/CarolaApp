package com.valnie.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Matrix;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;


public class MainActivity extends AppCompatActivity implements PauseFragment.PauseButtonCallback {

    //Il bottoncino di start dello splash screen
    private ImageButton startButton;


    //Bottoni che uso nel livello di gioco
    private ImageButton pauseButton;
    private ImageButton tastoni;
    private ClickedPauseNotifier cpn;
    private final int POSITIVE = 1;
    private final int NEGATIVE = -1;
    private final char X_POS = 'x';
    private final char Y_POS = 'y';




    //Variabile booleana che mi permette di indicare se il gioco è attivo o meno
    private boolean isPlaying;

    //il protagonista vero e proprio del nostro gioco: Mursik the cat!
    private ImageView cat;


    private Handler mHandler;
    private char pos;
    private int verse;
    private TextView timerText;



    //Questi sono i valori utili per il countdown, 120.000 equivale a 120 secondi,
    //Questo vale poiché il tempo è misurato in millisecondi dal countdown
    private static final long STARTING_TIME = 120000; //tempo in ms (millisecondi)

    private CountDownTimer timer;
    private long time_left = STARTING_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (ImageButton) findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScene();
            }
        });

    }

    /**
     * Questa funzione "avvia" la scena di gioco vera e propria.
     */
    protected void startScene() {
        System.out.println("I'm changing!!");
        setContentView(R.layout.game_level);
        cpn = new ClickedPauseNotifier(this);
        cat = (ImageView) findViewById(R.id.cat);
        time_left = STARTING_TIME;
        timerText = (TextView) findViewById(R.id.countdown);
//        startTimer();
        myStart();
        playButtons();
        myPause();
    }


    /**
     * Questa funzione imposta il tasto di pausa, indicando quale è la sua azione
     */

    protected void myPause() {
        PauseFragment pause = new PauseFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment,pause, "");
        ft.hide(pause).commit();
        pauseButton = (ImageButton) findViewById(R.id.pausa);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCountdown();
            }
        });
    }

    protected void myStart(){
        startButton = (ImageButton) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlaying)
                    startTimer();
            }
        });
    }

    // Tutta quest'area si occupa dei movimenti dei bottoni di gioco e di cosa 'azionano'

    private void playButtons() {
        ButtonHandler bh = new ButtonHandler();
        findViewById(R.id.upbutton).setOnTouchListener(bh);
        findViewById(R.id.downbutton).setOnTouchListener(bh);
        findViewById(R.id.leftbutton).setOnTouchListener(bh);
        findViewById(R.id.rightbutton).setOnTouchListener(bh);
        tastoni = (ImageButton) findViewById(R.id.tastoni);


    }

    @Override
    public void clickedPause() {
//        Toast.makeText(getBaseContext(),"onCallback Called",Toast.LENGTH_LONG).show();
        setCountdown();
    }


    /**
     * Il controllo onTouch si occupa di "animare" la pulsantiera
     */
    private class ButtonHandler implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motion) {

            if (isPlaying) {

                switch (view.getId()) {
                    case R.id.upbutton:

                        switch (motion.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                verse = NEGATIVE;
                                pos = Y_POS;
                                rotateCat(0);
                                if (mHandler != null) return true;
                                mHandler = new Handler();
                                mHandler.postDelayed(mAction, 500);

                                tastoni.setImageResource(R.drawable.pulsantierasu);
                                return true;
                            case MotionEvent.ACTION_UP:

                                if (mHandler == null) return true;
                                mHandler.removeCallbacks(mAction);
                                mHandler = null;
                                tastoni.setImageResource(R.drawable.pulsantieracentro);
                                return true;
                            case MotionEvent.ACTION_MOVE:
                                System.out.println(motion.getX() + " " + motion.getY());
                                return true;

                        }

                        break;
                    case R.id.downbutton:
                        switch (motion.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                verse = POSITIVE;
                                pos = Y_POS;
                                rotateCat(180);
                                if (mHandler != null) return true;
                                mHandler = new Handler();
                                mHandler.postDelayed(mAction, 500);
                                System.out.println("Sono il pulsante down!");
                                tastoni.setImageResource(R.drawable.pulsantieragiu);
                                return true;
                            case MotionEvent.ACTION_UP:
                                if (mHandler == null) return true;
                                mHandler.removeCallbacks(mAction);
                                mHandler = null;
                                tastoni.setImageResource(R.drawable.pulsantieracentro);
                                return true;
                        }

                        break;
                    case R.id.rightbutton:
                        switch (motion.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                verse = POSITIVE;
                                pos = X_POS;
                                rotateCat(270);
                                if (mHandler != null) return true;
                                mHandler = new Handler();
                                mHandler.postDelayed(mAction, 500);
                                tastoni.setImageResource(R.drawable.pulsantieradestra);
                                return true;
                            case MotionEvent.ACTION_UP:
                                if (mHandler == null) return true;
                                mHandler.removeCallbacks(mAction);
                                mHandler = null;
                                tastoni.setImageResource(R.drawable.pulsantieracentro);
                                return true;
                        }


                        break;
                    case R.id.leftbutton:
                        switch (motion.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                rotateCat(90);
                                verse = NEGATIVE;
                                pos = X_POS;
                                if (mHandler != null) return true;
                                mHandler = new Handler();
                                mHandler.postDelayed(mAction, 500);
                                tastoni.setImageResource(R.drawable.pulsantierasinistra);
                                return true;
                            case MotionEvent.ACTION_UP:
                                if (mHandler == null) return true;
                                mHandler.removeCallbacks(mAction);
                                mHandler = null;
                                tastoni.setImageResource(R.drawable.pulsantieracentro);
                                return true;
                        }

                        break;
                    default:

                        break;


                }
                return false;
            }


            return true;

        }


    }

//In questa zona ho dichiarato i metodi utili per creare il countdown


    public void setCountdown() {
        PauseFragment pause = new PauseFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragment,pause, "");


        if (isPlaying) {
            ft.show(pause);
            pauseTimer();

        } else {
            ft.hide(pause);
            startTimer();
        }
        ft.commit();
    }


    /**
     * La parte visuale del countdown è gestita da questa funzione, che restituisce il testo in
     * minuti e secondi
     */

    private void updateTimerText() {
        int minutes = (int) (time_left / 1000) / 60;
        int seconds = (int) (time_left / 1000) % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText(timeLeft);
    }


    private void startTimer() {
        timer = new CountDownTimer(time_left, 1000) {
            @Override
            public void onTick(long millisLeft) {
                time_left = millisLeft;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isPlaying = false;
            }
        }.start();
        isPlaying = true;
    }

    private void pauseTimer() {
        timer.cancel();
        isPlaying = false;

    }

    private void rotateCat(int angle){
        Matrix matrix = new Matrix();
        float pivotX = cat.getDrawable().getBounds().width()/2;
        float pivotY = cat.getDrawable().getBounds().height()/2;
        cat.setScaleType(ImageView.ScaleType.MATRIX);   //required

        matrix.postRotate((float) angle, pivotX, pivotY);
        cat.setImageMatrix(matrix);
    }
    /**
     * Questo runnable ti permette di controllare la velocità di movimento del nostro gattino.
     */
    Runnable mAction = new Runnable() {
        @Override
        public void run() {
            System.out.println("Performing action...");
            switch(pos){
                case 'x':
                    if(cat.getX() > -20 && verse == NEGATIVE)
                        cat.setX(cat.getX() + verse * 10);

                    else if(cat.getX() < 924 && verse == POSITIVE)
                        cat.setX(cat.getX() + verse * 10);
                    System.out.println(cat.getX());
                    break;
                case 'y':
                    if(cat.getY() > 170 && verse == NEGATIVE)
                        cat.setY(cat.getY() + verse * 10);

                    else if(cat.getY() < 1060 && verse == POSITIVE)
                    cat.setY(cat.getY() + verse * 10);


                    System.out.println(cat.getY());
                    break;
            }

            mHandler.postDelayed(this, 10);
        }


    };

}