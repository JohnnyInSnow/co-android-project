package cs.hung;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAB_KEY_INDEX = "tab_key";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // ActionBar
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // create new tabs and and set up the titles of the tabs
        ActionBar.Tab mteacherTab = actionbar.newTab().setText(
                getString(R.string.ui_tabname_teacher));
        ActionBar.Tab mstudentTab = actionbar.newTab().setText(
                getString(R.string.ui_tabname_student));
        ActionBar.Tab mgroupTab = actionbar.newTab().setText(
                getString(R.string.ui_tabname_group));
        // 12_06 revise

        // create the fragments
        Fragment mteacherFragment = new settingPage();
        Fragment mstudentmentFragment = new StudentFragment();
        Fragment mgroupFragment = new resultPage();

        // bind the fragments to the tabs - set up tabListeners for each tab
        mteacherTab.setTabListener(new MyTabsListener(mteacherFragment,
                getApplicationContext()));
        mstudentTab.setTabListener(new MyTabsListener(mstudentmentFragment,
                getApplicationContext()));
        mgroupTab.setTabListener(new MyTabsListener(mgroupFragment,
                getApplicationContext()));


        // add the tabs to the action bar
        actionbar.addTab(mteacherTab);
        actionbar.addTab(mstudentTab);
        actionbar.addTab(mgroupTab);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_search:
                Toast.makeText(this, getString(R.string.ui_menu_search),
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuitem_send:
                Toast.makeText(this, getString(R.string.ui_menu_send),
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuitem_add:
                Toast.makeText(this, getString(R.string.ui_menu_add),
                        Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuitem_feedback:
                Toast.makeText(this, getString(R.string.ui_menu_feedback),
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuitem_about:
                Toast.makeText(this, getString(R.string.ui_menu_about),
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuitem_quit:
                Toast.makeText(this, getString(R.string.ui_menu_quit),
                        Toast.LENGTH_SHORT).show();
                finish(); // close the activity
                return true;
        }
        return false;
    }



    //	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

}

// TabListenr class for managing user interaction with the ActionBar tabs. The
// application context is passed in pass it in constructor, needed for the
// toast.

class MyTabsListener implements ActionBar.TabListener {
    public Fragment fragment;
    public Context context;

    public MyTabsListener(Fragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {


    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {

        ft.replace(R.id.fragment_container, fragment);
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

        ft.remove(fragment);
    }

}