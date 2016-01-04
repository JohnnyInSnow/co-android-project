package cs.hung;

import cs.hung.R;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class levelPage extends Fragment {
    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String p1 = prefs.getString("p1Name", "");
        String p2 = prefs.getString("p2Name", "");
        int level = prefs.getInt("level", 0);
        if((p1.equals("")) || (p2.equals("")) || (level == 0)){
            root = inflater.inflate(R.layout.studentfragment, container, false);
            Log.e("message", "沒有完成輸入資料");
        }else{
            root = inflater.inflate(R.layout.game1layout, container, false);
            Log.e("message", "有完成輸入資料");

        }




        return root;
    }
    
}

