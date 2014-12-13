package il.bruzn.freelancers.Controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getFragmentManager().beginTransaction().add(R.id.menu_container, new MenuFragment()).commit();
		_drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		_toggle = new ActionBarDrawerToggle(this, _drawerLayout, R.string.menu_opened, R.string.menu_closed);
		_drawerLayout.setDrawerListener(_toggle); // Calls onDrawerOpened() & OnDrawerClosed() functions
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make the menu icon appear at top left of the screen

		boolean backstackEmpty = getFragmentManager().getBackStackEntryCount() == 0;
		if (backstackEmpty) // first time instancied
			menuItemClicked(MenuFragment.getMenu()[0]);
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

	public void setFragment(ItemFrag itemFrag){
		setFragment(itemFrag, true);
	}
	public void setFragment(ItemFrag itemFrag, boolean keepTransactions){
		getSupportActionBar().setTitle(itemFrag.getTitle());// Change the Activity title

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction .replace(R.id.main_container, itemFrag.getFragment());

		if (!keepTransactions)
			getFragmentManager().popBackStackImmediate("", FragmentManager.POP_BACK_STACK_INCLUSIVE);
		else
			transaction.addToBackStack("");
		transaction.commit();
//		_itemFrag =itemFrag;
	}

	@Override
	public void onBackPressed() {
		if (_drawerLayout.isDrawerOpen(GravityCompat.START))
			_drawerLayout.closeDrawers();
		else if (getFragmentManager().getBackStackEntryCount() > 0 ){
			getFragmentManager().popBackStack();
		} else {
			super.onBackPressed();
		}
	}

	private void disconnection(){
		ConnectedMember.nullMember();
		getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit().clear().commit();
		startActivity(new Intent(getApplicationContext(), SignInActivity.class));
//		_itemFrag = null;
		finish();
	}

	@Override
	public void menuItemClicked(MenuFragment.ItemMenu item) {
		if (item.getItemFrag().getFragment() == null){
			disconnection();
			return;
		}

		// Change the main Fragment
		getFragmentManager().popBackStackImmediate()
		if (item != MenuFragment.getMenu()[0])
			setFragment(item.getItemFrag());

		_drawerLayout.closeDrawers();
	}
}
