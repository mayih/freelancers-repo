package il.bruzn.freelancers.Controller.fragments;

import android.support.v4.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Modele.ConnectedMember;
import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.Entities.Message;
import il.bruzn.freelancers.Modele.Modele;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.ImageHelper;

/**
 * Created by Yair on 01/12/2014.
 */
public class MessagesFragment extends ListFragment implements TitledFragment {

	ArrayList<ArrayList<Message>> _listOfDiscussion;
//	private final static String KEY_LISTOFDISCUSSION = "key list of discussion in hashmap";

	@Override
	public String getTitle() {
		return "Messages";
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		getListView().setItemChecked(position, true);
		DiscussionFragment fragment = new DiscussionFragment();
		fragment.setMessages(_listOfDiscussion.get(position));
		((MainActivity) getActivity()).setFragment(fragment);
	}

	@Override
	public void onResume() {
		super.onResume();
		_listOfDiscussion = Modele.getMessageRepo().selectAllDiscussions(ConnectedMember.getMember());
		setListAdapter(new InboxArrayAdapter(_listOfDiscussion));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.inbox_actionbar, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case R.id.action_new_discussion:
				NewDiscussionFragment NewDiscussion = new NewDiscussionFragment();
				((MainActivity)getActivity()).setFragment(NewDiscussion);
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
}