package il.bruzn.freelancers.Controller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import il.bruzn.freelancers.R;

/**
 * Created by Yair on 05/01/2015.
 */
public class InboxPagerFragment extends Fragment implements TitledFragment {
	ViewPager _viewPager;
	Fragment[] _fragments;
	PagerAdapter _pagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_fragments = new Fragment[]{new MessagesFragment()};
		_pagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
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
		};
		for (Fragment frag : _fragments)
			frag.setHasOptionsMenu(true);
	}

	@Override
	public String getTitle() {
		return getResources().getString(R.string.inbox_actionbar_title);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		_viewPager = (ViewPager)inflater.inflate(R.layout.view_pager, container, false);
		_viewPager.setAdapter(_pagerAdapter);
		return _viewPager;
	}


}
