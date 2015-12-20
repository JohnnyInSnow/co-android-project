package cs.hung;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

/**
 * Created by johnnyofsnow on 15/12/20.
 */
public class initPage extends Activity {
    ImageButton enter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);

        enter = (ImageButton)findViewById(R.id.imageButton);
        enter.setOnClickListener(even1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    private View.OnClickListener even1 = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.imageButton:
                    Intent intent = new Intent();
                    intent.setClass(initPage.this, cs.hung.MainActivity.class);
                    startActivity(intent);
                    break;
                default:

            }
        }
    };

}
