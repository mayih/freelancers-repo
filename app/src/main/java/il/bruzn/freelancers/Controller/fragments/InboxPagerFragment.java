package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import il.bruzn.freelancers.Modele.Modele;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.Sleep;

/**
 * Created by Yair on 05/01/2015.
 */
public class InboxPagerFragment extends Fragment implements TitledFragment {
	ViewPager _viewPager;
	Fragment[] _fragments;
	final static String KEY_FRAGMENTS = "key for fragments in hashmap";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null){
			long hashMapKey = savedInstanceState.getLong(KEY_FRAGMENTS);
			_fragments = (Fragment[])Modele.getHashMap().get(hashMapKey);
			Modele.getHashMap().remove(hashMapKey);
		}
		else if (_fragments == null) {
			_fragments = new Fragment[]{new MessagesFragment(), new RequestFragment()};
			for (Fragment frag : _fragments)
				frag.setHasOptionsMenu(true);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		_viewPager = (ViewPager)inflater.inflate(R.layout.view_pager, container, false);
		_viewPager.setAdapter(new InboxPagerAdapter());
		return _viewPager;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Sleep.sleep(1);
		long hashMapKey = System.currentTimeMillis();
		Modele.getHashMap().put(hashMapKey, _fragments);
		outState.putLong(KEY_FRAGMENTS, hashMapKey);
	}

	@Override
	public String getTitle() {
		return getResources().getString(R.string.inbox_actionbar_title);
	}

	class InboxPagerAdapter extends FragmentPagerAdapter {
		InboxPagerAdapter() {
			super(getChildFragmentManager());
		}

		@Override
		public int getCount() {
			return _fragments.length;
		}

		@Override
		public Fragment getItem(int position) {
			if (position >=0 && position<_fragments.length)
				return _fragments[position];
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return ((TitledFragment)_fragments[position]).getTitle();
		}
	}
}
