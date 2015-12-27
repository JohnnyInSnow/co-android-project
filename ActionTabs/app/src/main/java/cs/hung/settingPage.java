package cs.hung;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.content.Context.MODE_PRIVATE;

public class settingPage extends Fragment {
	private RadioGroup levelNum;
    private RadioButton level3, level5, level7;
    private Button nameButton;
    private Button levelButton;
    private EditText p1Edit, p2Edit;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.setting, container, false);

        levelNum = (RadioGroup)root.findViewById(R.id.levelGroup);
        level3 = (RadioButton)root.findViewById(R.id.radioButton);
        level5 = (RadioButton)root.findViewById(R.id.radioButton2);
        level7 = (RadioButton)root.findViewById(R.id.radioButton3);
        nameButton = (Button)root.findViewById(R.id.btnNameSetting);
        levelButton = (Button)root.findViewById(R.id.btnLevelSetting);
        p1Edit = (EditText)root.findViewById(R.id.p1edit);
        p2Edit = (EditText)root.findViewById(R.id.p2edit);
        nameButton.setOnClickListener(new Onclick());
        preData();
        //level3.setOnClickListener(even1);
        //level5.setOnClickListener(even1);
        //level7.setOnClickListener(even1);
        return root;
    }

    // For this activity goBack from background
    // onRestart()->onStart()
    @Override
    public void onStart() {
        super.onStart();
        preData();
    }

    private void preData(){
        String p1 = getDefault("p1Name",getActivity());
        String p2 = getDefault("p2Name", getActivity());
        if(p1.equals("unknown")){
            p1Edit.setText("");
        }else{
            p1Edit.setText(p1);
        }

        if(p2.equals("unknown")){
            p2Edit.setText("");
        }else{
            p2Edit.setText(p2);
        }

    }

    // stackOverflow for fetching the saved values from all activities.
    public static void setDefaults(String key, String value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefault(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "unknown");
    }

    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.btnNameSetting){
                String tip = "";
                int state = 0;
                if((p1Edit.getText()).equals("")){
                    tip = tip + "玩家1名字未設定";
                    state++;
                }

                if((p2Edit.getText()).equals("")){
                    tip = tip + " 玩家2名字未設定";
                    state++;
                }

                //玩家名字都有輸入
                if(state == 0){
                    setDefaults("p1Name", p1Edit.getText().toString(), getActivity());
                    setDefaults("p2Name", p2Edit.getText().toString(), getActivity());
                    //p2Edit.setText(getDefault("p1Name", getActivity()));
                }

            }else if(v.getId() == R.id.btnLevelSetting){

            }else{

            }
        }
    }


}

