package lincyu.chapter6_horoscope2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetBirthdayActivity extends ActionBarActivity {

    EditText etMonth, etDay;

    public static final String KEY_MONTH = "KEY_MONTH";
    public static final String KEY_DAY = "KEY_DAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setbirthday);
        Button submit = (Button)findViewById(R.id.submit);
        etMonth = (EditText)findViewById(R.id.etMonth);
        etDay = (EditText)findViewById(R.id.etDay);
        submit.setOnClickListener(gethoroscope);
    }

    private OnClickListener gethoroscope = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String intMonth = new String();
            String intDay = new String();
            intMonth = etMonth.getText().toString();
             intDay = etDay.getText().toString();
           /* if (intMonth == intDay){
                Toast.makeText(SetBirthdayActivity.this,
                        R.string.input_error,
                        Toast.LENGTH_LONG).show();
                return;

            }*/
           /* if ((intMonth < 1 || intMonth > 12) ||
                    (intDay < 1 || intDay > 31)) {
                Toast.makeText(SetBirthdayActivity.this,
                        R.string.input_error,
                        Toast.LENGTH_SHORT).show();
                return;
            }*/

            Intent intent = new Intent();
            intent.setClass(SetBirthdayActivity.this,
                    HoroscopeActivity.class);
            intent.putExtra(KEY_MONTH, intMonth);
            intent.putExtra(KEY_DAY, intDay);
            startActivity(intent);
        }
    };
}
