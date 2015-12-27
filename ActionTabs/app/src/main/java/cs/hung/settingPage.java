package cs.hung;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class settingPage extends Fragment {
	private RadioGroup levelNum;
    private RadioButton level3, level5, level7;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.setting, container, false);

        levelNum = (RadioGroup)root.findViewById(R.id.levelGroup);
        level3 = (RadioButton)root.findViewById(R.id.radioButton);
        level5 = (RadioButton)root.findViewById(R.id.radioButton2);
        level7 = (RadioButton)root.findViewById(R.id.radioButton3);
        //level3.setOnClickListener(even1);
        //level5.setOnClickListener(even1);
        //level7.setOnClickListener(even1);


        return root;
    }


}

