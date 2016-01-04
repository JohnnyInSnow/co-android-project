package cs.hung;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by johnnyofsnow on 15/10/25.
 */
public class game1Handle extends Fragment {
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
    Thread t; // handle player1 thread
    Thread t2; // handle player2 thread
    Thread t3; // handle image thread
    MediaPlayer m;
    int count = 0;
    int count1 = 0;
    int count2 = 0;
    int game2End = 0;
    int level = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.game1layout, container, false);
        Log.e("message", "game1Handle ONcreate()");
        iv1 = (ImageView)root.findViewById(R.id.imageView);
        play1Time = (TextView)root.findViewById(R.id.player1Result);
        play2Time = (TextView)root.findViewById(R.id.player2Result);
        play1Rule = (TextView)root.findViewById(R.id.player1Hint);
        play2Rule = (TextView)root.findViewById(R.id.player2Hint);
        player1Btn = (Button)root.findViewById(R.id.player1Button);
        player2Btn = (Button)root.findViewById(R.id.player2Button);


        player1Btn.setOnClickListener(even1);
        player2Btn.setOnClickListener(even1);


        play1Rule.setText("聽到開始音效 請心中自數10秒後立刻按下stop");
        play2Rule.setText("聽到開始音效 請心中自數10秒後立刻按下stop");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        player1Btn.setClickable(false);
        player2Btn.setClickable(false);
        Log.e("message", "game1Handle ONSTART()");
        if(player1Stop == 2 && player2Stop == 2){
            init();
            m = MediaPlayer.create(getActivity(),R.raw.start);
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
            m = MediaPlayer.create(getActivity(),R.raw.start);
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
            m = MediaPlayer.create(getActivity(),R.raw.start);
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
        play1Time.setVisibility(View.INVISIBLE);
        play2Time.setVisibility(View.INVISIBLE);
        play1Rule.setText("聽到開始音效 請心中自數10秒後立刻按下stop");
        play2Rule.setText("聽到開始音效 請心中自數10秒後立刻按下stop");
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
        ThreadDeclaration();
        ThreadDeclaration2();
    }

    private void game2Start(){
        player1Stop = 0;
        player2Stop = 0;
        m = MediaPlayer.create(getActivity(),R.raw.start);
        // stop for 3 second.
        Timer time1 = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                ThreadDeclaration();
                ThreadDeclaration2();
                ThreadDeclaration3();
                m.start();
            }
        };
        time1.schedule(task1, 3000);

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

    private void ThreadDeclaration3() {
        t3 = new Thread() {
            @Override
            public void run() {
                //player2Btn.setClickable(false);
                for (count2 = 0; count2 <= 10; ++count2) {
                    if(game2End != 1){
                        iv1.setImageResource(imgIds[position]);
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
                    if(player1Stop != 2 && player1Stop != 1){
                        if(level == 1) {
                            player1Sec = checkCounter;
                            Log.e("message", "player1Sec = " + player1Sec);
                            count = 10001;
                            t.interrupt();
                            player1Stop = 1;
                            play1Time.setVisibility(View.VISIBLE);
                            int who = decideWiner();
                            switch (who) {
                                case 1:
                                    play1Rule.setText("player 1 Win!!");
                                    play2Rule.setText("player 1 Win!!");
                                    if (player2Sec > 1000) {
                                        play2Time.setText("超過10秒");
                                    }

                                    break;
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");
                                    if (player1Sec > 1000) {
                                        play1Time.setText("超過10秒");
                                    }
                                    break;
                                case 3:
                                    play1Rule.setText("dawn");
                                    play2Rule.setText("dawn");
                                    break;
                                default:

                                    break;
                            }
                        }
                    }
                    break;
                case R.id.player2Button:
                    if(player2Stop != 2 && player2Stop != 1) {
                        if (level == 1) {
                            player2Sec = checkCounter2;
                            Log.e("message", "player2Sec = " + player2Sec);
                            count1 = 10001;
                            t2.interrupt();
                            player2Stop = 1;
                            play2Time.setVisibility(View.VISIBLE);
                            int who = decideWiner();
                            switch (who) {
                                case 1:
                                    play1Rule.setText("player 1 Win!!");
                                    play2Rule.setText("player 1 Win!!");


                                    break;
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");

                                    break;
                                case 3:
                                    play1Rule.setText("dawn");
                                    play2Rule.setText("dawn");
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

    private int decideWiner(){
        int winner = 3;
        if(player1Stop == 1 && player2Stop == 1){
            if(player1Sec > 1000){
                if(player2Sec > 1000){
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();
                    return 3;
                }else if(player2Sec == 1000){
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();
                    return 2;
                }else{
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();
                    return 2;
                }
            }else if(player1Sec == 1000){
                if(player2Sec == 1000){
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();
                    return 3;
                }else{
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();
                    return 1;
                }
            }else{
                if(player1Sec < 1000 && player2Sec < 1000){
                    if(player1Sec > player2Sec){
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 1;
                    }else{
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 2;
                    }
                }else{
                    if(player1Sec < 1000){
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 1;
                    }else{
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 2;
                    }
                }
            }
        }else if(player1Stop == 1){
            if(checkCounter2 > 1000){
                count1 = 10001;
                t2.interrupt();
                player2Stop = 1;
                player2Sec = checkCounter2;

                play2Time.setVisibility(View.VISIBLE);
                play2Time.setText("超過10秒");
                m = MediaPlayer.create(getActivity(), R.raw.draw);
                m.start();

                return 1;
            }
        }else if(player2Stop == 1){
            if(checkCounter > 1000){
                count = 10001;
                t.interrupt();
                player1Stop = 1;
                player1Sec = checkCounter;
                play1Time.setVisibility(View.VISIBLE);
                play1Time.setText("超過10秒");
                m = MediaPlayer.create(getActivity(), R.raw.draw);
                m.start();

                return 2;
            }
        }else{

        }
        return 4;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("message", "game1Handle onDestroy()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("message", "game1Handle onResume()");
    }
/**
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("message", "onRestart()");

    }
*/
    @Override
    public void onPause() {
        super.onPause();
        Log.e("message", "onPause()");
    }
}
