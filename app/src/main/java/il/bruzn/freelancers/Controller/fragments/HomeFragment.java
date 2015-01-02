package il.bruzn.freelancers.Controller.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 30/11/2014.
 */
public class HomeFragment extends ListFragment implements TitledFragment {

	// Data
	ArrayList<Member> _listToPrint;

	@Override
	public String getTitle() {
		return "Home";
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		_listToPrint = Module.getMemberRepo().selectAll();

        HomeAdapter adapter = new HomeAdapter(_listToPrint);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Save the member
		Member memberSelected = ((HomeAdapter)getListAdapter()).getItem(position);
		long hashMapKey = System.currentTimeMillis();
		Module.getHashMap().put(hashMapKey, memberSelected);
		// Transfer the key
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ProfileFragment.MEMBER_KEY, hashMapKey);
        fragment.setArguments(bundle);
		// Launch fragment
        ((MainActivity)getActivity()).setFragment(fragment);
    }

	private class HomeAdapter extends ArrayAdapter<Member> {

        public HomeAdapter(ArrayList<Member> members)
        {
            super(getActivity(), 0, members);
        }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
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
			listviewofopinions.removeAllViews();
			for (Opinion op : subject.getOpinionsOnMe()){
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
