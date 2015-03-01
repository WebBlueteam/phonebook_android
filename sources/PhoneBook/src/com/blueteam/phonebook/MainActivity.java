/*
 *
 */
package com.blueteam.phonebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.blueteam.phonebook.fragments.MainChangePhoneFragment;
import com.blueteam.phonebook.utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends ActionBarActivity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startMainFragment();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Start fragment.
	 *
	 * @param fragment the fragment
	 * @param isAddBackStack the is add back stack
	 * @param tag the tag
	 */
	private void startFragment(Fragment fragment, boolean isAddBackStack, String tag){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment, tag);
		if(isAddBackStack){
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

	/**
	 * Start main fragment.
	 */
	public void startMainFragment(){
		MainChangePhoneFragment fragment = new MainChangePhoneFragment();
		startFragment(fragment, false, Constants.TAG_MAIN_FRAGMENT);
	}
}
