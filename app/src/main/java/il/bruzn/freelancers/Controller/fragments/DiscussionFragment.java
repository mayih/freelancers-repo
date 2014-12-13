package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 11/12/2014.
 */
public class DiscussionFragment extends Fragment {

	ListView _listView;
	private SimpleDateFormat _dateForm;

	private ArrayList<Message> _messages;
	public void setMessages(ArrayList<Message> messages) {
		_messages = messages;
	}

	public DiscussionFragment() {
		_dateForm =  new SimpleDateFormat("HH:mm", Locale.US);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_discussion, container, false);
		_listView = (ListView) layout.findViewById(R.id.messages_listview);
		_listView.setAdapter(new DiscussionAdapter(_messages));
		return layout;
	}

	public class DiscussionAdapter extends ArrayAdapter<Message> {
		public DiscussionAdapter(ArrayList<Message> list) {
			super(getActivity(), R.layout.item_message_self, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Message message = getItem(position);
			int layoutId;
			if (message.getAuthor() == ConnectedMember.getMember())
				layoutId = R.layout.item_message_self;
			else
				layoutId = R.layout.item_message_other;

			View itemLayout = getActivity().getLayoutInflater().inflate(layoutId, parent, false);

			((TextView)itemLayout.findViewById(R.id.time_message)).setText(_dateForm.format(message.getDate()));
			((TextView)itemLayout.findViewById(R.id.text_message)).setText(message.getText());

			return itemLayout;
		}
	}
}
