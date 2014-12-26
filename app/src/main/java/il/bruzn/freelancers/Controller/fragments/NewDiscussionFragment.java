package il.bruzn.freelancers.Controller.fragments;

import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
 * Created by Yair on 25/12/2014.
 */
public class NewDiscussionFragment extends ListFragment implements TitledFragment {

	ArrayList<Member> _listOfMember;
	private final static String KEY_LISTOFMEMBER = "key list of member in hashmap";

	@Override
	public String getTitle() {
		return getResources().getString(R.string.title_new_discussion);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LISTOFMEMBER)) {
			long hashMapKey = savedInstanceState.getLong(KEY_LISTOFMEMBER);
			_listOfMember = (ArrayList<Member>) Module.getHashMap().get(hashMapKey);
			Module.getHashMap().remove(KEY_LISTOFMEMBER);
		}
		else {
			_listOfMember = Module.getMemberRepo().selectAll();
			for (Member member: _listOfMember)
				if (member.getId() == ConnectedMember.getMember().getId()) {
					_listOfMember.remove(member);
					break;
				}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new MembersArrayAdapter(_listOfMember));
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Member selectedMember = _listOfMember.get(position);
				DiscussionFragment newDiscussion = new DiscussionFragment();
				newDiscussion.setInterlocutor(selectedMember);
				getActivity().getFragmentManager().popBackStack();
				((MainActivity)getActivity()).setFragment(newDiscussion);
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		long hashMapKey = System.currentTimeMillis();
		Module.getHashMap().put(hashMapKey, _listOfMember);
		outState.putLong(KEY_LISTOFMEMBER, hashMapKey);
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
