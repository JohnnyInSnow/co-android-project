package com.example.johnnyofsnow.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by johnnyofsnow on 15/10/25.
 */
public class gameHandle extends Activity {
    private TextView play1Time;
    private TextView play2Time;
    private TextView play1Rule;
    private TextView play2Rule;
    ImageView iv1;
    Button player1Btn;
    Button player2Btn;
    int player1Stop = 2;
    int player2Stop = 2;
    int checkCounter = 0;
    int checkCounter2 = 0;
    int player1Sec = 0;
    int player2Sec = 0;
    int position = 0;
    private int imgIds[] = {
            R.drawable.graps,R.drawable.orange,R.drawable.str
    };
    private Runnable runnableForImage;
    private static Runnable runnableRef;
    private static Handler handlerForImage;
    private static MyHandler m1;

    Thread t; // handle player1 thread
    Thread t2; // handle player2 thread
    Thread t3; // handle image thread
    MediaPlayer m;
    int count = 0;
    int count1 = 0;
    int count2 = 0;
    int game2End = 0;
    int level = 1;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        Log.e("message", "ONcreate()");
        iv1 = (ImageView)findViewById(R.id.imageView);
        play1Time = (TextView)findViewById(R.id.player1Result);
        play2Time = (TextView)findViewById(R.id.player2Result);
        play1Rule = (TextView)findViewById(R.id.player1Hint);
        play2Rule = (TextView)findViewById(R.id.player2Hint);
        player1Btn = (Button)findViewById(R.id.player1Button);
        player2Btn = (Button)findViewById(R.id.player2Button);


        player1Btn.setOnClickListener(even1);
        player2Btn.setOnClickListener(even1);


        play1Rule.setText("聽到開始音效 看到草莓立刻按下stop");
        play2Rule.setText("聽到開始音效 看到草莓立刻按下stop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        player1Btn.setClickable(false);
        player2Btn.setClickable(false);
        iv1.setImageResource(0);
        Log.e("message", "ONSTART()");
        if(player1Stop == 2 && player2Stop == 2){
            init();
            m = MediaPlayer.create(this,R.raw.start);
            // stop for 5 second.
            Timer time1 = new Timer();
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    gameStart();
                    m.start();
                    player1Btn.setClickable(true);
                    player2Btn.setClickable(true);
                }
            };
            time1.schedule(task1, 5000);

            //play1Time.setTextColor(Color.rgb(249, 255, 134));
        }else if(player1Stop == 1 && player2Stop == 1){
            init();
            player1Btn.setClickable(false);
            player2Btn.setClickable(false);
            m = MediaPlayer.create(this,R.raw.start);
            // stop for 5 second.
            Timer time1 = new Timer();
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    gameStart();
                    m.start();
                    player1Btn.setClickable(true);
                    player2Btn.setClickable(true);
                }
            };
            time1.schedule(task1, 5000);

        }else{
            init();
            player1Btn.setClickable(false);
            player2Btn.setClickable(false);
            m = MediaPlayer.create(this,R.raw.start);
            // stop for 5 second.
            Timer time1 = new Timer();
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    gameStart();
                    m.start();
                    player1Btn.setClickable(true);
                    player2Btn.setClickable(true);
                }
            };
            time1.schedule(task1, 5000);
        }
    }

    private void ready(){


    }

    private void init(){
        play1Time.setTextColor(Color.rgb(249,255,134));
        play2Time.setTextColor(Color.rgb(164, 255, 176));
        play1Rule.setText("聽到開始音效 看到草莓立刻按下stop");
        play2Rule.setText("聽到開始音效 看到草莓立刻按下stop");
        player1Stop = 0;
        player2Stop = 0;
        checkCounter = 0;
        checkCounter2 = 0;
        player1Sec = 0;
        player2Sec = 0;
        count = 0;
        count1 = 0;
        level = 1;

    }

    private void gameStart(){
        player1Stop = 0;
        player2Stop = 0;
        game2End = 0;
        i = 0;
        ThreadDeclaration();
        ThreadDeclaration2();
        changeImage();


        //ThreadDeclaration3();
    }


/**
    private Runnable runnableRef = new Runnable() {
        @Override
        public void run() {

            //i++;
            iv1.postDelayed(this, 1000);
            handlerForImage.postDelayed(runnableRef, 1000);
        }
    };*/


    private void changeImage(){
        iv1.postDelayed(runnableForImage = new Runnable() {
            @Override
            public void run() {
                if (game2End != 1) {
                    i = (int) (Math.random() * 100);
                    Log.e("message", "outer i = " + i);
                    if (i >= 0 && i < 15) {
                        i = 2;
                    } else {
                        if (i < 60) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                    }
                    Log.e("message", "inner i = " + i);
                    iv1.setImageResource(imgIds[i]);

                } else {
                    runnableForImage = null;
                }
                iv1.postDelayed(this, 800);
            }
        },800);
    }




    private void ThreadDeclaration() {
        t = new Thread() {
            @Override
            public void run() {
                //player1Btn.setClickable(false);
                for (count = 0; count <= 10000; ++count) {
                    if(player1Stop != 1) {
                        sendMessage(1, checkCounter);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                    checkCounter=count+1;
                }
                player1Btn.setClickable(true);
                checkCounter=0;
            }
        };
        t.start();
    }

    private void ThreadDeclaration2() {
        t2 = new Thread() {
            @Override
            public void run() {
                //player2Btn.setClickable(false);
                for (count1 = 0; count1 <= 10000; ++count1) {
                    if(player2Stop != 1){
                        sendMessage(2, checkCounter2);
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                    checkCounter2=count1+1;
                }
                player2Btn.setClickable(true);
                checkCounter2=0;
            }
        };
        t2.start();
    }
/**
    private void ThreadDeclaration3() {
        t3 = new Thread() {
            @Override
            public void run() {
                //player2Btn.setClickable(false);
                for (count2 = 0; count2 <= 10; ++count2) {
                    if(game2End != 1){
                        iv1.setImageResource(getResources().getDrawable(imgIds[position]));
                        position = (int)((Math.random() * 30) % 3);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                    if(count2 == 9){
                        count2 = 0;
                    }
                }
            }
        };
        t3.start();
    }
*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle b = msg.getData();
                    double time1 = (double)(b.getInt("COUNT_VALUE") / 100.0);
                    play1Time.setText("" + time1 + "秒");
                    if(player2Stop == 1 && count > 10000 && player1Sec > 1000){
                        play1Time.setText("超過10秒");
                    }
                    break;
                case 2:
                    Bundle b2 = msg.getData();
                    double time2 = (double)(b2.getInt("COUNT_VALUE") / 100.0);
                    play2Time.setText("" + time2 + "秒");
                    if(player1Stop == 1 && count1 > 10000 && player2Sec > 1000){
                        play2Time.setText("超過10秒");
                    }
                    break;
                case 3:
            }
        }
    };

    private void sendMessage(int msg, int count) {
        Message message = new Message();
        message.what = msg;//message code
        Bundle b = new Bundle();
        b.putInt("COUNT_VALUE", count);
        message.setData(b);
        handler.sendMessage(message);
    }

    private View.OnClickListener even1 = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.player1Button:
                    //game2End = 1;
                    if(player1Stop != 2 && player1Stop != 1){
                        if(level == 1) {
                            //player1Sec = checkCounter;
                            //Log.e("message", "player1Sec = " + player1Sec);

                            //play1Time.setTextColor(Color.rgb(0, 0, 0));
                            int who = decideWiner(1);
                            switch (who) {
                                case 1:
                                    play1Rule.setText("player 1 Win!!");
                                    play2Rule.setText("player 1 Win!!");
                                    if (player2Sec > 1000) {
                                        play2Time.setText("超過10秒");
                                    }
                                    //game2End = 1;
                                    break;
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");
                                    if (player1Sec > 1000) {
                                        play1Time.setText("超過10秒");
                                    }
                                    //game2End = 1;
                                    break;
                                case 3:
                                    play1Rule.setText("dawn");
                                    play2Rule.setText("dawn");
                                    //game2End = 1;
                                    break;
                                default:

                                    break;
                            }
                        }
                    }
                    break;
                case R.id.player2Button:
                    //game2End = 1;
                    if(player2Stop != 2 && player2Stop != 1) {
                        if (level == 1) {
                            //player2Sec = checkCounter2;
                            //Log.e("message", "player2Sec = " + player2Sec);

                            //play2Time.setTextColor(Color.rgb(0, 0, 0));
                            int who = decideWiner(2);
                            switch (who) {
                                case 1:
                                    play1Rule.setText("player 1 Win!!");
                                    play2Rule.setText("player 1 Win!!");

                                    //game2End = 1;
                                    break;
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");
                                    //game2End = 1;
                                    break;
                                case 3:
                                    play1Rule.setText("dawn");
                                    play2Rule.setText("dawn");
                                    //game2End = 1;
                                    break;
                                default:

                                    break;
                            }
                        }
                    }
                    break;
            }
        }
    };

    private int decideWiner(int who){
        //int winner = 0;
        if(i == 2){
            game2End = 1;
            //m1.stopMyHandler();
            count = 10001;
            t.interrupt();
            player1Stop = 1;
            count1 = 10001;
            t2.interrupt();
            player2Stop = 1;
            m = MediaPlayer.create(this,R.raw.draw);
            m.start();
            return who;
        }else{
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("message", "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MyHandler.resumeMyHandler(myRunnable);
        Log.e("message", "onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("message", "onRestart()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        //MyHandler.pauseMyHandler(myRunnable);
        Log.e("message", "onPause()");
    }
}


