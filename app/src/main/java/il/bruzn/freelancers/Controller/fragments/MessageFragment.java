package il.bruzn.freelancers.Controller.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.Locale;

import il.bruzn.freelancers.Model.ConnectedMember;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Message;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ImageHelper;
import il.bruzn.freelancers.basic.Sleep;
import il.bruzn.freelancers.basic.ToRun;

/**
 * Created by Yair on 11/12/2014.
 */
public class MessageFragment extends Fragment  implements TitledFragment {

	private ListView _listView;

	private Member _interlocutor;
	private ArrayList<Message> _messages;

	private static final String KEY_FOR_MESSAGES = "keyForMessagesInHashMap";
	private static final String KEY_FOR_INTERLOCUTOR = "keyForInterlocutorInHashMap";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.US);

	public MessageFragment() {
		_messages = null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState!=null) {
			if (savedInstanceState.containsKey(KEY_FOR_MESSAGES)) {
				long hashMapKey = savedInstanceState.getLong(KEY_FOR_MESSAGES);
				_messages = (ArrayList<Message>) Model.getHashMap().get(hashMapKey);
				Model.getHashMap().remove(hashMapKey);
			}

			if (savedInstanceState.containsKey(KEY_FOR_INTERLOCUTOR)) {
				long hashMapKey = savedInstanceState.getLong(KEY_FOR_INTERLOCUTOR);
				_interlocutor = (Member) Model.getHashMap().get(hashMapKey);
				Model.getHashMap().remove(hashMapKey);
			}
		}

		// Get the interlocutor if messages exists
		if (_messages != null && !_messages.isEmpty() && _interlocutor==null) {
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
				new AsyncToRun<Void>()
						.setMain(addMessage)
						.setPost(null)
						.execute(message);
				// Clear the EditText
				editText.getText().clear();
				// Update the ListView
				_messages.add(message);
				// Close Keyboard
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				// At the end of the listView
				_listView.setSelection(_listView.getCount() - 1);
			}
		});
		return layout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		_listView.setSelection(_listView.getCount() - 1); // Scroll to bottom of list
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		long hashMapKey = Sleep.sleep(1); // Key to store messages
		Model.getHashMap().put(hashMapKey, _messages); // Store the list
		outState.putLong(KEY_FOR_MESSAGES, hashMapKey); // Save the key

		hashMapKey = Sleep.sleep(1); // Key to store interlocutor
		Model.getHashMap().put(hashMapKey, _interlocutor); // Store the interlocutor
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
			if (message.getAuthor().getId() == ConnectedMember.getMember().getId()) {
				layoutId = R.layout.item_message_self;
				Bitmap pictureInterlocutor = ImageHelper.getRoundedCornerBitmap(message.getAuthor().getPicture(), 100);
				if (pictureInterlocutor != null && convertView != null)
					((ImageView)convertView.findViewById(R.id.picture_item_message)).setImageBitmap(pictureInterlocutor);
			}
			else
				layoutId = R.layout.item_message_other;

			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(layoutId, parent, false);

			((TextView)convertView.findViewById(R.id.time_message)).setText(DATE_FORMAT.format(message.getDate()));
			((TextView)convertView.findViewById(R.id.text_message)).setText(message.getText());

			return convertView;
		}
	}

	@Override
	public String getTitle() {
		return _interlocutor.getFirstName() + " " + _interlocutor.getLastName();
	}

	// Setters  ---
	public MessageFragment setMessages(ArrayList<Message> messages) {
		_messages = messages;
		return this;
	}
	public MessageFragment setInterlocutor(Member interlocutor) {
		_interlocutor = interlocutor;
		if (_messages == null) {
			new AsyncToRun<Void>()
					.setMain(selectDiscussion)
					.setPost(null)
					.execute();
		}

		return this;
	}

	private ToRun<Void> addMessage = new ToRun<Void>(){
		@Override
		public Void run(Object... parameters){
			if (parameters.length > 0 &&
					parameters[0] != null &&
					parameters[0].getClass() == Message.class) {
				Model.getMessageRepo().add((Message)parameters[0]);
			}
			return null;
		}
	};

	private ToRun<Void> selectDiscussion = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			_messages = Model.getMessageRepo().selectDiscussion(ConnectedMember.getMember(), _interlocutor);
			return null;
		}
	};
}
