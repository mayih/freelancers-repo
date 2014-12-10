package il.bruzn.freelancers.Controller;

import android.app.Fragment;

/**
 * Created by Yair on 10/12/2014.
 * An ItemFrag is a class which contains a Fargment & its title (to print on the action bar).
 * This class was made for the MainActivity.setFragment() funciton.
 */
public class ItemFrag {
	private Fragment _frag;
	private String _title;

	public Fragment getFragment() {
		return _frag;
	}
	public String getTitle() {
		return _title;
	}

	public ItemFrag setFragment(Fragment frag) {
		_frag = frag;
		return this;
	}
	public ItemFrag setTitle(String title) {
		_title = title;
		return this;
	}
}
