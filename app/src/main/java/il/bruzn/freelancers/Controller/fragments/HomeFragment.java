package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.Module.ConnectedMember;

/**
 * Created by Yair on 30/11/2014.
 */
public class HomeFragment extends ListFragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_home, container, false);
		return layout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getListView().setAdapter(new HomeAdapter());
	}

	private class HomeAdapter extends ArrayAdapter<Member>{

		public HomeAdapter() {
			super(getActivity().getApplicationContext(), R.layout.item_profile, Module.get_memberRepo().selectAll());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Member subject = getItem(position);

			View profileItem = getActivity().getLayoutInflater().inflate(R.layout.item_profile, parent, false);
			TextView text = (TextView) profileItem.findViewById(R.id.item_profile_name);
			text.setText(subject.getFirstName() +" "+ subject.getLastName());

			text = (TextView) profileItem.findViewById(R.id.item_profile_mark);
			text.setText(subject.getAverage() + " / 5");

			return profileItem;
		}
	}
}
