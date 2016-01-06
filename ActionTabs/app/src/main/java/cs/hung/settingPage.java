package cs.hung;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class settingPage extends Fragment {
	private RadioGroup levelNum;
    private RadioButton level3, level5, level7;
    private Button nameButton;
    private Button levelButton;
    private EditText p1Edit, p2Edit;
    private TextView insDisplay;
    SharedPreferences preferences;
    boolean name1Check = false;
    boolean name2Check = false;
    boolean levelCheck = false;

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
        insDisplay = (TextView)root.findViewById(R.id.instruction);
        nameButton.setOnClickListener(new Onclick());
        levelButton.setOnClickListener(new Onclick());
        level3.setOnClickListener(new Onclick());
        level5.setOnClickListener(new Onclick());
        level7.setOnClickListener(new Onclick());

        preData();
        getInstruction();
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
        name1Check = false;
        name2Check = false;
        levelCheck = false;
        preData();
        getInstruction();
    }



    public void preData(){
        String p1 = getDefault("p1Name", getActivity());
        String p2 = getDefault("p2Name", getActivity());
        int level = getDefaultForInt("level", getActivity());

        if(p1.equals("")){
            p1Edit.setText("");
            name1Check = false;
        }else{
            p1Edit.setText(p1);
            name1Check = true;
        }

        if(p2.equals("")){
            p2Edit.setText("");
            name1Check = false;
        }else{
            p2Edit.setText(p2);
            name2Check = true;
        }

        if(level == 0){
            levelCheck = false;
            level3.setChecked(false);
            level5.setChecked(false);
            level7.setChecked(false);
        }else{
            levelCheck = true;
            if(level == 1){
                level3.setChecked(true);
            }else if(level == 2){
                level5.setChecked(true);
            }else if(level == 3){
                level7.setChecked(true);
            }else{

            }
        }
    }

    private void getInstruction(){
        String temp = "";
        int count = 0;
        if((name1Check == true) && (name2Check == true)){
            count++;
        }else{
            temp = "玩家名字未設定好";

        }

        if(levelCheck == true){
            count++;
        }else{

            temp = temp + ",,關卡未設定";
        }

        if(count == 2){
            insDisplay.setText("遊戲所需要的資料已經設定完成，\n請按下畫面上的game按鈕開始遊戲");
        }else{
            insDisplay.setText(temp);
        }
    }


    // stackOverflow for fetching the saved values from all activities.
    //Overloadding for matching parameters demand and it's will be invoked
    // For String data writes to .xml file
    public static void setDefaults(String key, String value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //Overloadding for matching parameters demand and it's will be invoked
    // For int data writes to .xml file
    public static void setDefaults(String key, int value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getDefault(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static int getDefaultForInt(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
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
                preData();
                getInstruction();

            }else if(v.getId() == R.id.btnLevelSetting){

            }else if(v.getId() == R.id.radioButton){
                //Toast.makeText(getActivity(),"三關",Toast.LENGTH_SHORT).show();
                setDefaults("level", 1, getActivity());
                preData();
                getInstruction();
            }else if(v.getId() == R.id.radioButton2){
                //Toast.makeText(getActivity(),"五關",Toast.LENGTH_SHORT).show();
                setDefaults("level", 2, getActivity());
                preData();
                getInstruction();
            }else if(v.getId() == R.id.radioButton3){
                //Toast.makeText(getActivity(),"七關",Toast.LENGTH_SHORT).show();
                setDefaults("level", 3, getActivity());
                preData();
                getInstruction();
            }else{

            }
        }
    }


}

