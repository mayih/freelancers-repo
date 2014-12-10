package il.bruzn.freelancers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.ConnectedMember;

/**
 * Created by Yair on 30/11/2014.
 */
public class HomeFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_home, container, false);
		return layout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


		TextView welcome = (TextView)getActivity().findViewById(R.id.welcometext);
		welcome.setText("Welcome " + ConnectedMember.getMember().getEmail());
	}
}
