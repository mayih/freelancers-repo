package il.bruzn.freelancers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;

import il.bruzn.freelancers.basic.ConnectedMember;
import il.bruzn.freelancers.basic.ItemMenu;
import il.bruzn.freelancers.fragments.HomeFragment;
import il.bruzn.freelancers.fragments.MenuFragment;


public class MainActivity extends ActionBarActivity implements MenuFragment.iMenulistener {

	ActionBarDrawerToggle _toggle;
	DrawerLayout _drawerLayout;
	Fragment	_mainFrament;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		_toggle = new ActionBarDrawerToggle(this, _drawerLayout, R.string.menu_opened, R.string.menu_closed);
		_drawerLayout.setDrawerListener(_toggle); // Calls onDrawerOpened() & OnDrawerClosed() functions

		menuItemClicked(MenuFragment.getMenu()[0].getFragment());
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

	private void disconnection(){
		ConnectedMember.nullMember();
		getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit().clear().commit();
		startActivity(new Intent(getApplicationContext(), SignInActivity.class));
		finish();
	}

	@Override
	public void menuItemClicked(Fragment item) {
		if (item == null){
			disconnection();
			return;
		}
		_mainFrament = item;
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		transaction	.replace(R.id.main_container, _mainFrament)
					.commit();
		_drawerLayout.closeDrawers();
	}
}
