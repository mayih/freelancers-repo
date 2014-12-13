package il.bruzn.freelancers.Controller.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import il.bruzn.freelancers.Controller.ItemFrag;
import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 30/11/2014.
 */
public class HomeFragment extends ListFragment {

	ArrayList<Member> _listToPrint;

	public HomeFragment() {
		_listToPrint = Module.getMemberRepo().selectAll();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ListView layout = (ListView)inflater.inflate(R.layout.fragment_home, container, false);
		layout.setAdapter(new HomeAdapter());
		return layout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Member memberSelected = _listToPrint.get(position);
				ProfileFragment fragment = new ProfileFragment();
				Bundle bundle = new Bundle();
				bundle.putString(ProfileFragment.EMAIL_MEMBER_KEY, memberSelected.getEmail());
				fragment.setArguments(bundle);
				((MainActivity)getActivity()).setFragment(new ItemFrag().setFragment(fragment).setTitle(memberSelected.getFirstName()));
			}
		});
	}

	private class HomeAdapter extends ArrayAdapter<Member> {

		public HomeAdapter() {
			super(getActivity().getApplicationContext(), R.layout.item_profile, _listToPrint);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getActivity().getLayoutInflater().inflate(R.layout.item_profile, parent, false);

			Member subject = Module.getOpnionRepo().fillMember(getItem(position));

			// Set the header of the view (name + average mark)
			TextView text = (TextView) convertView.findViewById(R.id.item_profile_name);
			text.setText(subject.getFirstName() +" "+ subject.getLastName());
			text = (TextView) convertView.findViewById(R.id.item_profile_mark);
			if (subject.getAverage()> 0)
				text.setText(subject.getAverage() + " / 5");
			else
				text.setText("");

			// set the inner listView
			LinearLayout listviewofopinions = (LinearLayout) convertView.findViewById(R.id.list_opinons_in_profile_item);
			for (Opinion op:subject.getOpinionsOnMe()){
				View opinionItem = getActivity().getLayoutInflater().inflate(R.layout.item_opinion, null, false);
				((TextView)opinionItem.findViewById(R.id.item_opinion_name)).setText(op.getAuthor().getFirstName()+" "+op.getAuthor().getLastName());
				((TextView)opinionItem.findViewById(R.id.item_opinion_mark)).setText(op.getLevel().getValue()+"");
				((TextView)opinionItem.findViewById(R.id.item_opinion_text)).setText(op.getText());
				listviewofopinions.addView(opinionItem);
			}

			return convertView;
		}

	}
}
