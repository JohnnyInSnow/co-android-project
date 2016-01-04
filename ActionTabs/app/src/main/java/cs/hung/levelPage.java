package cs.hung;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class levelPage extends Fragment {
    View root;
    FragmentManager fm;
    ArrayList<Fragment> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String p1 = prefs.getString("p1Name", "");
        String p2 = prefs.getString("p2Name", "");
        int level = prefs.getInt("level", 0);
        list = new ArrayList<>();
        list.add(new game2Handle());
        fm = getChildFragmentManager(); // This line not wrong that it's require api 17 for using.
        if((p1.equals("")) || (p2.equals("")) || (level == 0)){

            root = inflater.inflate(R.layout.studentfragment, container, false);

            Log.e("message", "開啟空畫面");
        }else{
            FragmentTransaction childFragTrans = fm.beginTransaction();
            if(level == 3){
                game1Handle game1 = new game1Handle();
                childFragTrans.add(R.id.FL,game1);
                childFragTrans.addToBackStack("game1");
            }else if(level == 5){
                game2Handle game2 = new game2Handle();
                childFragTrans.add(R.id.FL,game2);
                childFragTrans.addToBackStack("game2");
            }else{
                game1Handle game1 = new game1Handle();
                childFragTrans.add(R.id.FL,game1);
                childFragTrans.addToBackStack("game1");
            }

            childFragTrans.commit();
            root = inflater.inflate(R.layout.studentfragment, container, false);
            // Although the fragment should be the game, but it needs the container(studentfragment) to hold it.
            Log.e("message", "開啟子畫面");
        }
        return root;
    }
}

