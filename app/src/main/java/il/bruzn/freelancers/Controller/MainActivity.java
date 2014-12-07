package il.bruzn.freelancers.Controller;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.Controller.fragments.MenuFragment;


public class MainActivity extends ActionBarActivity implements MenuFragment.iMenulistener {

	ActionBarDrawerToggle _toggle;
	DrawerLayout _drawerLayout;

	// Save on wich fragment it is
	int	_mainFramentId = 0;
	static final String KEY_OF_MAIN_FRAGMENT = "main_fragment_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(savedInstanceState != null && savedInstanceState.containsKey(KEY_OF_MAIN_FRAGMENT))
			_mainFramentId = savedInstanceState.getInt(KEY_OF_MAIN_FRAGMENT);

		_drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		_toggle = new ActionBarDrawerToggle(this, _drawerLayout, R.string.menu_opened, R.string.menu_closed);
		_drawerLayout.setDrawerListener(_toggle); // Calls onDrawerOpened() & OnDrawerClosed() functions

		menuItemClicked(_mainFramentId);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make the menu icon appear at top left of the screen
	}

	/* *
	* It allows the sync between the drawerlayout and the menu icon _toggle (drawer listener).
	* This function "onPostCreate" is called after all the resume, start & restart functions.
	* I put the sync inside and not at the onCreate so it sync even after a rotation (restart).
	* */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		_toggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return  _toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_OF_MAIN_FRAGMENT ,_mainFramentId); // Save on which fragment it left
	}

	private void disconnection(){
		ConnectedMember.nullMember();
		getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit().clear().commit();
		startActivity(new Intent(getApplicationContext(), SignInActivity.class));
		finish();
	}

	@Override
	public void menuItemClicked(int itemId) {
		_mainFramentId = itemId;
		Fragment fragment = MenuFragment.getMenu()[itemId].getFragment();
		if (fragment == null){
			disconnection();
			return;
		}

		// Change the Activity title
		getSupportActionBar().setTitle(MenuFragment.getMenu()[itemId].getText());
		// Change the main Fragment
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction	.replace(R.id.main_container, fragment)
					.commit();
		_drawerLayout.closeDrawers();
	}
}
