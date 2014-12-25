package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.ImageHelper;

/**
 * Created by Yair on 01/12/2014.
 */
public class InboxFragment extends ListFragment implements TitledFragment {
	ArrayList<ArrayList<Message>> _listOfDiscussions;

	@Override
	public String getTitle() {
		return "Inbox";
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = getListView();
		_listOfDiscussions = Module.getMessageRepo().selectAllDiscussions(ConnectedMember.getMember());
		setListAdapter(new InboxArrayAdapter(_listOfDiscussions));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getListView().setItemChecked(position, true);
				DiscussionFragment fragment = new DiscussionFragment();
				fragment.setMessages(_listOfDiscussions.get(position));
				((MainActivity) getActivity()).setFragment(fragment);
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.inbox_actionbar, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.action_new_discussion:
				// Get the list of member to show
				final ArrayList<Member> members = Module.getMemberRepo().selectAll();

				// Remove the connected member from the list
				for (Member member:members){
					if (member.getId() == ConnectedMember.getMember().getId()){
						members.remove(member);
						break;
					}
				}

				setListAdapter(new MembersArrayAdapter(members));
				getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Member selectedMember = members.get(position);
						DiscussionFragment newDiscussion = new DiscussionFragment();
						newDiscussion.setInterlocutor(selectedMember);
						((MainActivity)getActivity()).setFragment(newDiscussion);
					}
				});
		        return true;
		    default:
				return super.onOptionsItemSelected(item);
		}
	}

	// The arrayList passed to the constructor contains the discussions. A discussion is a list of messages sorted by dates.
	private class InboxArrayAdapter extends ArrayAdapter<ArrayList<Message>> {

		public InboxArrayAdapter(List<ArrayList<Message>> discussions) {
			super(getActivity(), 0,discussions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_discussion, parent, false);

			// Identify the interlocutor
			Member interlocutor;
			if (getItem(position).get(0).getAuthor().getId() != ConnectedMember.getMember().getId())
				interlocutor = getItem(position).get(0).getAuthor();
			else
				interlocutor = getItem(position).get(0).getReceiver();

			// Fill convertView
			TextView nameView = (TextView) convertView.findViewById(R.id.name_item_discussion);
			nameView.setText(interlocutor.getFirstName() + " " + interlocutor.getLastName());

			ImageView pictureView = (ImageView) convertView.findViewById(R.id.picture_item_discussion);
			Bitmap picture;
			if (interlocutor.getPicture() != null)
				picture = interlocutor.getPicture();
			else
				picture = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
			picture = ImageHelper.getRoundedCornerBitmap(picture, 100);
			pictureView.setImageBitmap(picture);

			return convertView;
		}
	}
	private class MembersArrayAdapter extends ArrayAdapter<Member> {
		private MembersArrayAdapter(List<Member> objects) {
			super(getActivity(), 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_discussion, parent, false);

			// Identify the interlocutor
			Member interlocutor = getItem(position);

			// Fill convertView
			TextView nameView = (TextView) convertView.findViewById(R.id.name_item_discussion);
			nameView.setText(interlocutor.getFirstName() + " " + interlocutor.getLastName());

			ImageView pictureView = (ImageView) convertView.findViewById(R.id.picture_item_discussion);
			Bitmap picture;
			if (interlocutor.getPicture() != null)
				picture = interlocutor.getPicture();
			else
				picture = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
			picture = ImageHelper.getRoundedCornerBitmap(picture, 100);
			pictureView.setImageBitmap(picture);

			return convertView;
		}

	}
}