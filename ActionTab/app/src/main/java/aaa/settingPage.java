package aaa;

/**
 * Created by User on 2015/12/8.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class settingPage extends Fragment {
    private RadioGroup levelNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(com.example.user.actiontab.R.layout.setting, container, false);

        levelNum = (RadioGroup)root.findViewById(com.example.user.actiontab.R.id.levelGroup);

        return inflater.inflate(com.example.user.actiontab.R.layout.setting, container, false);
    }

}
