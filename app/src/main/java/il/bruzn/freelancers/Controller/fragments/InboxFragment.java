package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Controller.ItemFrag;
import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 01/12/2014.
 */
public class InboxFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_inbox, container, false);
		return layout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		final ListView listView = (ListView)getActivity().findViewById(R.id.discussions_listview);
		final ArrayList<ArrayList<Message>> listOfDiscussions = Module.getMessageRepo().selectAllDiscussions(ConnectedMember.getMember());
		listView.setAdapter(new InboxArrayAdapter(listOfDiscussions));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listView.setItemChecked(position, true);
				DiscussionFragment fragment = new DiscussionFragment();
				fragment.setMessages(listOfDiscussions.get(position));
				((MainActivity) getActivity()).setFragment(new ItemFrag().setTitle("Discussion").setFragment(fragment));
			}
		});
	}

	// The arrayList passed to the constructor contains the discussions. A discussion is a list of messages sorted by dates.
	private class InboxArrayAdapter extends ArrayAdapter<ArrayList<Message>>{

		public InboxArrayAdapter(List<ArrayList<Message>> discussions) {
			super(getActivity(), R.layout.item_discussion,discussions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView discussionView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.item_discussion, parent, false);
			Member interlocutor;
			if (getItem(position).get(0).getAuthor() == ConnectedMember.getMember())
				interlocutor = getItem(position).get(0).getReceiver();
			else
				interlocutor = getItem(position).get(0).getAuthor();

			discussionView.setText(interlocutor.getFirstName()+" "+interlocutor.getLastName());
			return discussionView;
		}
	}
}