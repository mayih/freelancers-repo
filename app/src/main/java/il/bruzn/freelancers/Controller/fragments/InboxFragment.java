package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
public class InboxFragment extends Fragment  implements TitledFragment {

	@Override
	public String getTitle() {
		return "Inbox";
	}

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
				((MainActivity) getActivity()).setFragment(fragment);
			}
		});
	}

	// The arrayList passed to the constructor contains the discussions. A discussion is a list of messages sorted by dates.
	private class InboxArrayAdapter extends ArrayAdapter<ArrayList<Message>>{

		public InboxArrayAdapter(List<ArrayList<Message>> discussions) {
			super(getActivity(), 0,discussions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_discussion, parent, false);

			// Identify the interlocutor
			Member interlocutor;
			if (getItem(position).get(0).getAuthor() == ConnectedMember.getMember())
				interlocutor = getItem(position).get(0).getReceiver();
			else
				interlocutor = getItem(position).get(0).getAuthor();

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