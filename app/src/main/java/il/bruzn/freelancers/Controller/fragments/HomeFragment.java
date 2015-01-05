package il.bruzn.freelancers.Controller.fragments;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.Entities.Opinion;
import il.bruzn.freelancers.Modele.Modele;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 30/11/2014.
 */
public class HomeFragment extends ListFragment implements TitledFragment {

	// Data
	private ArrayList<Member> _listToPrint;
	private boolean _isUpToDate;

	@Override
	public String getTitle() {
		return "Home";
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		_isUpToDate = false;
    }

	@Override
	public void onResume() {
		super.onResume();
		if (!_isUpToDate){
			_listToPrint = Modele.getMemberRepo().selectAll();

			HomeAdapter adapter = new HomeAdapter(_listToPrint);
			setListAdapter(adapter);
			_isUpToDate = true;
		}

	}

	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Save the member
		Member memberSelected = ((HomeAdapter)getListAdapter()).getItem(position);
		long hashMapKey = System.currentTimeMillis();
		Modele.getHashMap().put(hashMapKey, memberSelected);
		// Transfer the key
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ProfileFragment.MEMBER_KEY, hashMapKey);
        fragment.setArguments(bundle);
		// Launch fragment
        ((MainActivity)getActivity()).setFragment(fragment);
    }

	public HomeFragment isToUpdate() {
		_isUpToDate = false;
		return this;
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

			Member subject = Modele.getOpnionRepo().fillMember(getItem(position));

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
