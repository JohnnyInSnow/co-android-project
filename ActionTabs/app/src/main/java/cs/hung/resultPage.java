package cs.hung;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class resultPage extends Fragment {
	TextView p1display;
    TextView p2display;
    Button allClear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.result, container,false);
        p1display = (TextView)root.findViewById(R.id.p1Result);
        p2display = (TextView)root.findViewById(R.id.p2Result);
        allClear = (Button)root.findViewById(R.id.btnClearAll);
        allClear.setOnClickListener(new Onclick());
        dataLoad();

        return root;
    }

    private void dataLoad(){
        settingPage obj = new settingPage();

        String p1name = obj.getDefault("p1Name", getActivity());
        String p2name = obj.getDefault("p2Name",getActivity());
        int level = obj.getDefaultForInt("level", getActivity());

        if(p1name.equals("")){
            p1display.setText("Player1");
        }else{
            p1display.setText(p1name);
        }

        if(p2name.equals("")){
            p2display.setText("Player2");
        }else{
            p2display.setText(p2name);
        }

        if(level == 0){

        }else{
            String temp = p1display.getText().toString();
            temp = temp + "(共" + level + "關)";
            p1display.setText(temp);

            temp = p2display.getText().toString();
            temp = temp + "(共" + level + "關)";
            p2display.setText(temp);
        }
        //p1display.setText("12");
        //p2display.setText("45");
    }

    public void clearAll(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.btnClearAll){
                AlertDialog.Builder alertWindow = new AlertDialog.Builder(getActivity());

                alertWindow.setTitle("確認清除所有資料");

                alertWindow
                        .setMessage("按下確認將會刪除玩家名字以及遊玩的所有紀錄")
                        .setCancelable(false)
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    clearAll();
                                    dataLoad();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = alertWindow.create();

                        alertDialog.show();
            }else{

            }
        }
    }
    
}

