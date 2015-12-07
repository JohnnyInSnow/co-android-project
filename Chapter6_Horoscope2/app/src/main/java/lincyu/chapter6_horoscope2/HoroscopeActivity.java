package lincyu.chapter6_horoscope2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HoroscopeActivity extends ActionBarActivity {

    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);
        Button backbtn = (Button)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(back);
        result = (TextView)findViewById(R.id.result);

        Intent intent = this.getIntent();

        String month = intent.getStringExtra("KEY_MONTH");
        String day = intent.getStringExtra("KEY_DAY");

        result.setText(setHoroscope( month , day));
    }

    private OnClickListener back = new OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public String setHoroscope(String intMonth, String intDay) {

        String x = new String();
        x = "player1 :"+intMonth+"\nplayer2 :"+intDay;

        return x;


       /* if (intDay < bound[intMonth-1]) {
            result.setText(resId[intMonth-1]);
        } else {
            if (intMonth == 12) {
                result.setText(resId[0]);

            } else {
                result.setText(resId[intMonth]);
            }
        }*/
    }



}

