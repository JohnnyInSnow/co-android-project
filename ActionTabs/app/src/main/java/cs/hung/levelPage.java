package cs.hung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class levelPage extends Fragment {
    View root;
    FragmentManager fm;
    ArrayList<Fragment> list;
    FragmentTransaction childFragTrans;
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
    int level = 0;
    int i = 1;
    private Handler h1 = new Handler();
    private Runnable runnableForImage = new Runnable() {
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
                Log.e("message", "removeCallBack()");
                //iv1.removeCallbacks();
            }
            h1.postDelayed(runnableForImage, 1200);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("message", "levelPage.java onCreateView()");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String p1 = prefs.getString("p1Name", "");
        String p2 = prefs.getString("p2Name", "");
        level = prefs.getInt("level", 0);
        preGrade();

        if((p1.equals("")) || (p2.equals("")) || (level == 0)) {

            root = inflater.inflate(R.layout.studentfragment, container, false);
            Log.e("message", "開啟空畫面");
        }else if(level == 1){
            level = 1;
            root = inflater.inflate(R.layout.game1layout, container, false);
            Log.e("message", "fragment can load");
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
        }else if(level == 2){
            level = 2;
            root = inflater.inflate(R.layout.game2layout, container, false);
            iv1 = (ImageView)root.findViewById(R.id.imageView);
            //play1Time = (TextView)root.findViewById(R.id.player1Result);
            //play2Time = (TextView)root.findViewById(R.id.player2Result);
            play1Rule = (TextView)root.findViewById(R.id.player1Hint);
            play2Rule = (TextView)root.findViewById(R.id.player2Hint);
            player1Btn = (Button)root.findViewById(R.id.player1Button);
            player2Btn = (Button)root.findViewById(R.id.player2Button);


            player1Btn.setOnClickListener(even1);
            player2Btn.setOnClickListener(even1);


            play1Rule.setText("聽到開始音效 看到草莓立刻按下stop");
            play2Rule.setText("聽到開始音效 看到草莓立刻按下stop");

            h1.removeCallbacks(runnableForImage);
            h1.postDelayed(runnableForImage,5000);
        }else{
            level = 3;
            root = inflater.inflate(R.layout.studentfragment, container, false);
        }
        return root;
    }

    public void preGrade(){
        settingPage obj = new settingPage();
        int p1Win = obj.getDefaultForInt("p1Win", getActivity());
        int p2Win = obj.getDefaultForInt("p2Win", getActivity());
        int p1Lose = obj.getDefaultForInt("p1Lose", getActivity());
        int p2Lose = obj.getDefaultForInt("p2Lose", getActivity());

        if((p1Win == 0) && (p2Win == 0) && (p1Lose == 0) && (p2Lose == 0)){
            obj.setDefaults("p1Win", 1, getActivity());
            obj.setDefaults("p2Win", 1, getActivity());
            obj.setDefaults("p1Lose", 1, getActivity());
            obj.setDefaults("p2Lose", 1, getActivity());
        }
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
                                    play2Time.setVisibility(View.INVISIBLE);
                                    if (player2Sec > 1000) {
                                        play2Time.setText("超過10秒");
                                    }
                                    settingPage obj = new settingPage();
                                    int p1Win = obj.getDefaultForInt("p1Win", getActivity());
                                    int p2Lose = obj.getDefaultForInt("p2Lose", getActivity());
                                    p1Win++;
                                    p2Lose++;
                                    obj.setDefaults("p1Win", p1Win, getActivity());
                                    obj.setDefaults("p2Lose", p2Lose, getActivity());

                                    break;
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");
                                    play1Time.setVisibility(View.INVISIBLE);
                                    if (player1Sec > 1000) {
                                        play1Time.setText("超過10秒");
                                    }
                                    settingPage obj1 = new settingPage();
                                    int p2Win = obj1.getDefaultForInt("p2Win", getActivity());
                                    int p1Lose = obj1.getDefaultForInt("p1Lose", getActivity());
                                    p2Win++;
                                    p1Lose++;
                                    obj1.setDefaults("p2Win", p2Win, getActivity());
                                    obj1.setDefaults("p1Lose", p1Lose, getActivity());
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
                    if(level == 2){
                        if(game2End != 1) {
                            Log.e("message", "level2  player2 click button");
                            int who = decideWiner(1);
                            switch (who) {
                                case 1:
                                    play1Rule.setText("player 1 Win!!");
                                    play2Rule.setText("player 1 Win!!");
                                    settingPage obj = new settingPage();
                                    int p1Win = obj.getDefaultForInt("p1Win", getActivity());
                                    int p2Lose = obj.getDefaultForInt("p2Lose", getActivity());
                                    p1Win++;
                                    p2Lose++;
                                    obj.setDefaults("p1Win", p1Win, getActivity());
                                    obj.setDefaults("p2Lose", p2Lose, getActivity());
                                    //game2End = 1;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }else if(level == 3){

                    }else{

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
                                    play2Time.setVisibility(View.INVISIBLE);
                                    settingPage obj = new settingPage();
                                    int p1Win = obj.getDefaultForInt("p1Win", getActivity());
                                    int p2Lose = obj.getDefaultForInt("p2Lose", getActivity());
                                    p1Win++;
                                    p2Lose++;
                                    obj.setDefaults("p1Win", p1Win, getActivity());
                                    obj.setDefaults("p2Lose", p2Lose, getActivity());

                                    break;
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");
                                    play1Time.setVisibility(View.INVISIBLE);
                                    settingPage obj1 = new settingPage();
                                    int p2Win = obj1.getDefaultForInt("p2Win", getActivity());
                                    int p1Lose = obj1.getDefaultForInt("p1Lose", getActivity());
                                    p2Win++;
                                    p1Lose++;
                                    obj1.setDefaults("p2Win", p2Win, getActivity());
                                    obj1.setDefaults("p1Lose", p1Lose, getActivity());
                                    break;
                                case 3:
                                    play1Rule.setText("dawn");
                                    play2Rule.setText("dawn");
                                    break;
                                default:

                                    break;
                            }
                        }else {

                        }
                    }
                    if(level == 2){
                        if(game2End != 1) {
                            int who = decideWiner(2);
                            switch (who) {
                                case 2:
                                    play1Rule.setText("player 2 Win!!");
                                    play2Rule.setText("player 2 Win!!");
                                    settingPage obj1 = new settingPage();
                                    int p2Win = obj1.getDefaultForInt("p2Win", getActivity());
                                    int p1Lose = obj1.getDefaultForInt("p1Lose", getActivity());
                                    p2Win++;
                                    p1Lose++;
                                    obj1.setDefaults("p2Win", p2Win, getActivity());
                                    obj1.setDefaults("p1Lose", p1Lose, getActivity());
                                    //game2End = 1;
                                    break;
                                default:

                                    break;
                            }
                        }
                    }else if(level == 3){

                    }else{

                    }
                    break;
            }
        }
    };
    private int decideWiner() {
        if (level == 1) {


            int winner = 3;
            if (player1Stop == 1 && player2Stop == 1) {
                if (player1Sec > 1000) {
                    if (player2Sec > 1000) {
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 3;
                    } else if (player2Sec == 1000) {
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 2;
                    } else {
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 2;
                    }
                } else if (player1Sec == 1000) {
                    if (player2Sec == 1000) {
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 3;
                    } else {
                        m = MediaPlayer.create(getActivity(), R.raw.draw);
                        m.start();
                        return 1;
                    }
                } else {
                    if (player1Sec < 1000 && player2Sec < 1000) {
                        if (player1Sec > player2Sec) {
                            m = MediaPlayer.create(getActivity(), R.raw.draw);
                            m.start();
                            return 1;
                        } else {
                            m = MediaPlayer.create(getActivity(), R.raw.draw);
                            m.start();
                            return 2;
                        }
                    } else {
                        if (player1Sec < 1000) {
                            m = MediaPlayer.create(getActivity(), R.raw.draw);
                            m.start();
                            return 1;
                        } else {
                            m = MediaPlayer.create(getActivity(), R.raw.draw);
                            m.start();
                            return 2;
                        }
                    }
                }
            } else if (player1Stop == 1) {
                if (checkCounter2 > 1000) {
                    count1 = 10001;
                    t2.interrupt();
                    player2Stop = 1;
                    player2Sec = checkCounter2;

                    //play2Time.setVisibility(View.VISIBLE);
                    //play2Time.setText("超過10秒");
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();

                    return 1;
                }
            } else if (player2Stop == 1) {
                if (checkCounter > 1000) {
                    count = 10001;
                    t.interrupt();
                    player1Stop = 1;
                    player1Sec = checkCounter;
                    //play1Time.setVisibility(View.VISIBLE);
                    //play1Time.setText("超過10秒");
                    m = MediaPlayer.create(getActivity(), R.raw.draw);
                    m.start();

                    return 2;
                }
            } else {

            }
            return 4;
        }
        return 4;
    }

    private int decideWiner(int who){
        //int winner = 0;
        if(i == 2){
            game2End = 1;
            m = MediaPlayer.create(getActivity(),R.raw.draw);
            m.start();
            Log.e("message", "game2 end.");
            h1.removeCallbacks(runnableForImage);
            return who;
        }else{
            return 0;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("message", "level page onStart");
        if(level == 1){
            player1Btn.setClickable(false);
            player2Btn.setClickable(false);
            Log.e("message", "game1Handle ONSTART()");
            if(player1Stop == 2 && player2Stop == 2){
                init1();
                m = MediaPlayer.create(getActivity(),R.raw.start);
                // stop for 5 second.
                Timer time1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        game1Start();
                        m.start();
                        player1Btn.setClickable(true);
                        player2Btn.setClickable(true);
                    }
                };
                time1.schedule(task1, 5000);
                //play1Time.setTextColor(Color.rgb(249, 255, 134));
            }else if(player1Stop == 1 && player2Stop == 1){
                init1();
                player1Btn.setClickable(false);
                player2Btn.setClickable(false);
                m = MediaPlayer.create(getActivity(),R.raw.start);
                // stop for 5 second.
                Timer time1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        game1Start();
                        m.start();
                        player1Btn.setClickable(true);
                        player2Btn.setClickable(true);
                    }
                };
                time1.schedule(task1, 5000);

            }else{
                init1();
                player1Btn.setClickable(false);
                player2Btn.setClickable(false);
                m = MediaPlayer.create(getActivity(),R.raw.start);
                // stop for 5 second.
                Timer time1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        game1Start();
                        m.start();
                        player1Btn.setClickable(true);
                        player2Btn.setClickable(true);
                    }
                };
                time1.schedule(task1, 5000);
            }
        }else if(level == 2){
            player1Btn.setClickable(false);
            player2Btn.setClickable(false);
            iv1.setImageResource(0);
            Log.e("message", "ONSTART()");
            if(player1Stop == 2 && player2Stop == 2){
                init2();
                m = MediaPlayer.create(getActivity(),R.raw.start);
                // stop for 5 second.
                Timer time1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        game2Start();
                        m.start();
                        player1Btn.setClickable(true);
                        player2Btn.setClickable(true);
                    }
                };
                time1.schedule(task1, 5000);

                //play1Time.setTextColor(Color.rgb(249, 255, 134));
            }else if(player1Stop == 1 && player2Stop == 1){
                init2();
                player1Btn.setClickable(false);
                player2Btn.setClickable(false);
                m = MediaPlayer.create(getActivity(),R.raw.start);
                // stop for 5 second.
                Timer time1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        game2Start();
                        m.start();
                        player1Btn.setClickable(true);
                        player2Btn.setClickable(true);
                    }
                };
                time1.schedule(task1, 5000);

            }else{
                init2();
                player1Btn.setClickable(false);
                player2Btn.setClickable(false);
                m = MediaPlayer.create(getActivity(),R.raw.start);
                // stop for 5 second.
                Timer time1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        game2Start();
                        m.start();
                        player1Btn.setClickable(true);
                        player2Btn.setClickable(true);
                    }
                };
                time1.schedule(task1, 5000);
            }
        }else if(level == 3){

        }else{

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Fragment current = (Fragment)fm.findFragmentById(R.id.FL);
        //childFragTrans.remove(current);
        //childFragTrans.commit();
        if(level == 2){
            h1.removeCallbacks(runnableForImage);
        }

        Log.e("message", "level page onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("message", "level page onResume");
    }

    private void init1(){
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

    private void game1Start(){
        player1Stop = 0;
        player2Stop = 0;
        ThreadDeclaration();
        ThreadDeclaration2();
    }

    private void init2(){
        //play1Time.setTextColor(Color.rgb(249,255,134));
        //play2Time.setTextColor(Color.rgb(164, 255, 176));
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
        level = 2;

    }

    private void game2Start(){
        Log.e("message", "game2Start() ");
        player1Stop = 0;
        player2Stop = 0;
        game2End = 0;
        i = 0;
        //ThreadDeclaration();
        //ThreadDeclaration2();
        //h1 = new Handler();
        //h1.removeCallbacks(runnableForImage);
        //Log.e("message", "h1.removeCallbacks(runnableForImage);");
        //h1.postDelayed(runnableForImage,100);
        //changeImage();


        //ThreadDeclaration3();
    }
/**
    private void changeImage(){
        /**
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
                    Log.e("message", "removeCallBack()");
                    iv1.removeCallbacks();
                }
                iv1.postDelayed(this, 800);
            }
        }, 800);

        Log.e("message", "game2Start() for imageChange invoke");


    }
*/
}

