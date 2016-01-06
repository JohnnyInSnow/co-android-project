package cs.hung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
    int level = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("message", "levelPage.java onCreateView()");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String p1 = prefs.getString("p1Name", "");
        String p2 = prefs.getString("p2Name", "");
        int level = prefs.getInt("level", 0);
        /**
        list = new ArrayList<>();
        list.add(new game2Handle());
        fm = getChildFragmentManager(); // This line not wrong that it's require api 17 for using.
        if((p1.equals("")) || (p2.equals("")) || (level == 0)){



            Log.e("message", "開啟空畫面");
        }else{
            childFragTrans = fm.beginTransaction();
            if(level == 3){
                Log.e("message", "第一關");
                game1Handle game1 = new game1Handle();
                childFragTrans.replace(R.id.FL, game1);
                //childFragTrans.addToBackStack("game1");
            }else if(level == 5){
                Log.e("message", "第二關");
                game2Handle game2 = new game2Handle();
                childFragTrans.replace(R.id.FL, game2);
                //childFragTrans.addToBackStack("game2");
            }else{
                Log.e("message", "第三關");
                game1Handle game1 = new game1Handle();
                childFragTrans.replace(R.id.FL, game1);
                //childFragTrans.addToBackStack("game1");
            }
            Log.e("message", "fragment commit之前");
            childFragTrans.commit();
            root = inflater.inflate(R.layout.studentfragment, container, false);
            // Although the fragment should be the game, but it needs the container(studentfragment) to hold it.
            Log.e("message", "開啟子畫面");
        }*/
        if((p1.equals("")) || (p2.equals("")) || (level == 0)) {

            root = inflater.inflate(R.layout.studentfragment, container, false);
            Log.e("message", "開啟空畫面");
        }else if(level == 3){
            root = inflater.inflate(R.layout.game1layout, container, false);
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
        }else if(level == 5){
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
        }else{
            root = inflater.inflate(R.layout.studentfragment, container, false);
        }
        return root;
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
    public void onStart() {
        super.onStart();
        Log.e("message", "level page onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Fragment current = (Fragment)fm.findFragmentById(R.id.FL);
        //childFragTrans.remove(current);
        //childFragTrans.commit();

        Log.e("message", "level page onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("message", "level page onResume");
    }
}

