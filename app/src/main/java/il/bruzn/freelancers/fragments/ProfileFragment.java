package il.bruzn.freelancers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import il.bruzn.freelancers.R;

/**
 * Created by Yair on 01/12/2014.
 */
public class ProfileFragment  extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_profile, container, false);
		return layout;
	}
}
