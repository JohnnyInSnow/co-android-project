package cs.hung;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class resultPage extends Fragment {
	TextView p1display;
    TextView p2display;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.result, container,false);
        p1display = (TextView)root.findViewById(R.id.p1Result);
        p2display = (TextView)root.findViewById(R.id.p2Result);
        p1display.setText("12");
        p2display.setText("45");

        return root;
    }
    
}

