package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 11/12/2014.
 */
public class DiscussionFragment extends Fragment  implements TitledFragment {

	private ListView _listView;
	private SimpleDateFormat _dateForm;

	private Member _interlocutor;
	public DiscussionFragment setInterlocutor(Member interlocutor) {
		_interlocutor = interlocutor;
		if (_messages == null)
			_messages = Module.getMessageRepo().selectDiscussion(ConnectedMember.getMember(), interlocutor);
		return this;
	}

	private ArrayList<Message> _messages;
	public DiscussionFragment setMessages(ArrayList<Message> messages) {
		_messages = messages;
		return this;
	}

	private static final String KEY_FOR_MESSAGES = "keyForMessagesInHashMap";
	private static final String KEY_FOR_INTERLOCUTOR = "keyForInterlocutorInHashMap";

	public DiscussionFragment() {
		_dateForm =  new SimpleDateFormat("HH:mm", Locale.US);
		_messages = null;
	}

	@Override
	public String getTitle() {
		return "Discussion";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState!=null) {
			if (savedInstanceState.containsKey(KEY_FOR_MESSAGES)) {
				long hashMapKey = savedInstanceState.getLong(KEY_FOR_MESSAGES);
				_messages = (ArrayList<Message>) Module.getHashMap().get(hashMapKey);
				Module.getHashMap().remove(hashMapKey);
			}
			else if (savedInstanceState.containsKey(KEY_FOR_INTERLOCUTOR)) {
				long hashMapKey = savedInstanceState.getLong(KEY_FOR_MESSAGES);
				_interlocutor = (Member) Module.getHashMap().get(hashMapKey);
				Module.getHashMap().remove(hashMapKey);
			}
		}

		// Get the interlocutor
		if (!_messages.isEmpty()) {
			if (_messages.get(0).getAuthor().getId() != ConnectedMember.getMember().getId())
				_interlocutor = _messages.get(0).getAuthor();
			else
				_interlocutor = _messages.get(0).getReceiver();
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View layout = inflater.inflate(R.layout.fragment_discussion, container, false);

		_listView = (ListView) layout.findViewById(R.id.messages_listview);
		_listView.setAdapter(new DiscussionAdapter(_messages));

		ImageView sendIcon = (ImageView) layout.findViewById(R.id.send_icon);
		sendIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get the message
				EditText editText = (EditText)layout.findViewById(R.id.message_edittext);
				String text = editText.getText().toString();
				// Send the message
				Message message = new Message(ConnectedMember.getMember(),_interlocutor,text);
				Module.getMessageRepo().add(message);
				// Clear the EditText
				editText.getText().clear();
				// Update the ListView
				_messages.add(message);
				// Close Keyboard
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				// At the end of the listView
				_listView.setSelection(_listView.getCount()-1);
			}
		});
		return layout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		_listView.setSelection(_listView.getCount()-1); // Scroll to bottom of list
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		long hashMapKey = System.currentTimeMillis(); // Key for the list to store
		if (!_messages.isEmpty()) {
			Module.getHashMap().put(hashMapKey, _messages); // Store the list
			outState.putLong(KEY_FOR_MESSAGES, hashMapKey); // Save the key
		}
		Module.getHashMap().put(hashMapKey, _messages); // Store the interlocutor
		outState.putLong(KEY_FOR_INTERLOCUTOR, hashMapKey); // Save the key

		super.onSaveInstanceState(outState);
	}

	public class DiscussionAdapter extends ArrayAdapter<Message> {
		public DiscussionAdapter(ArrayList<Message> list) {
			super(getActivity(), R.layout.item_message_self, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Message message = getItem(position);
			int layoutId;
			if (message.getAuthor().getId() == ConnectedMember.getMember().getId())
				layoutId = R.layout.item_message_self;
			else
				layoutId = R.layout.item_message_other;

			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(layoutId, parent, false);

			((TextView)convertView.findViewById(R.id.time_message)).setText(_dateForm.format(message.getDate()));
			((TextView)convertView.findViewById(R.id.text_message)).setText(message.getText());

			return convertView;
		}
	}
}
