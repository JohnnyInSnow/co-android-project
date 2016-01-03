package cs.hung;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {
	public ActionBar.Tab mteacherTab;
	public ActionBar.Tab mstudentTab;
	public ActionBar.Tab mgroupTab;
	private static final String TAB_KEY_INDEX = "tab_key";
	private EditText p1;
	private EditText p2;
	private Button buttonplayer;
	settingPage obj;

	/** Called when the activity is first crePlugins Suggestion
	 Unknown features (Run Configuration[AndroidRunConfigurationType], Facet[android, android-gradle]) covered by disabled plugin detected.
	 Enable plugins...
	 Ignore Unknown Features
	 ated. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ActionBar
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// create new tabs and and set up the titles of the tabs
		mteacherTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_teacher));
		mstudentTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_student));
		mgroupTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_group));
		// 12_06 revise

		// create the fragments
		Fragment mteacherFragment = new settingPage();
		Fragment mstudentmentFragment = new levelPage();
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

/**
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

	*/

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
		switch (tab.getPosition()){
			case 0:
				ft.replace(R.id.fragment_container, fragment);
				break;
			case 1:
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
				String p1 = prefs.getString("p1Name", "");
				String p2 = prefs.getString("p2Name","");
				int level = prefs.getInt("level", 0);

				if((p1.equals("")) || (p2.equals("")) || (level == 0)){
					ft.replace(R.id.fragment_container, fragment);
					Log.e("message", "沒有完成輸入資料");
				}else{
					ft.replace(R.id.fragment_container, fragment);
					Log.e("message", "有完成輸入資料");

				}
				break;
			case 2:
				ft.replace(R.id.fragment_container, fragment);
				break;
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
		ft.remove(fragment);
	}

}