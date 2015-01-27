package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Model.ConnectedMember;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Request;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ImageHelper;
import il.bruzn.freelancers.basic.ToRun;

/**
 * Created by Yair on 06/01/2015.
 */
public class RequestReceivedFragment extends ListFragment implements TitledFragment {

	ArrayList<Request> _listOfRequests;
	public static final int REQUEST_MESSAGE = 1;
	public static final String DIALOG_MESSAGE = "Request Received";
	private View _currentView;
	private Request _currentRequest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AsyncToRun()
				.setMain(getAllRequest)
				.setPost(postGetAllRequest)
				.execute();
//		getListView().setDivider(null);
	}

	@Override
	public String getTitle() {
		return "Requests received";
	}

	class RequestFragmentAdapter extends ArrayAdapter<Request> {

		RequestFragmentAdapter(List<Request> list) {
			super(getActivity(), 0, list);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_request, parent, false);

			// Identify the interlocutor
			Member sender = getItem(position).getAuthor();

			// Fill convertView
			TextView nameView = (TextView) convertView.findViewById(R.id.item_request_name);
			nameView.setText(sender.getFirstName() + " " + sender.getLastName());

			ImageView pictureView = (ImageView) convertView.findViewById(R.id.request_profile_pic);
			Bitmap picture;
			if (sender.getPicture() != null)
				picture = sender.getPicture();
			else
				picture = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
			picture = ImageHelper.getRoundedCornerBitmap(picture, 100);
			pictureView.setImageBitmap(picture);

			TextView messageView = (TextView) convertView.findViewById(R.id.request_message_text);
			messageView.setText(getItem(position).getText());

			Button viewButton = (Button) convertView.findViewById(R.id.request_view_button);

			Button done = (Button) convertView.findViewById(R.id.request_done_button);
			TextView status = (TextView) convertView.findViewById(R.id.request_message_status);

			if (getItem(position).getIsAccepted() == Boolean.TRUE) {
				requestAccepted(convertView);
				if (getItem(position).getisDone())
				{
					requestDone(convertView);
				}
			}else if(getItem(position).getIsAccepted() == Boolean.FALSE)
			{
				requestRefused(convertView);
			}
			else {
				//State Initial
				done.setVisibility(View.INVISIBLE);
				done.setEnabled(false);
				viewButton.setVisibility(View.VISIBLE);
				viewButton.setEnabled(true);
				status.setVisibility(View.VISIBLE);
				status.setText("To answer...");
			}

			final View finalConvertView = convertView;
			done.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getItem(position).setDone(true);
					requestDone(finalConvertView);
					Model.getRequestRepo().update(getItem(position), getItem(position).getId());
				}
			});

			viewButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					_currentView = finalConvertView;
					_currentRequest = getItem(position);
					FragmentManager fm = getActivity().getSupportFragmentManager();
					AnswerRequestFragment dialog = AnswerRequestFragment.newInstance(_currentRequest.getText());
					dialog.setTargetFragment(RequestReceivedFragment.this, REQUEST_MESSAGE);
					dialog.show(fm, DIALOG_MESSAGE);
				}
			});
			return convertView;
		}
	}

	private void requestAccepted(View v){
		((TextView)v.findViewById(R.id.request_message_status)).setVisibility(View.INVISIBLE);

		((Button)v.findViewById(R.id.request_done_button)).setEnabled(true);
		((Button)v.findViewById(R.id.request_done_button)).setVisibility(View.VISIBLE);

		((Button)v.findViewById(R.id.request_view_button)).setVisibility(View.INVISIBLE);
		((Button)v.findViewById(R.id.request_view_button)).setEnabled(false);
	}

	private void requestRefused(View v){
		((TextView)v.findViewById(R.id.request_message_status)).setText("Refused");

		((Button)v.findViewById(R.id.request_view_button)).setVisibility(View.INVISIBLE);
		((Button)v.findViewById(R.id.request_view_button)).setEnabled(false);
	}

	private void requestDone(View v){
		((Button)v.findViewById(R.id.request_done_button)).setEnabled(false);
		((Button)v.findViewById(R.id.request_done_button)).setText("Is done");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED ) return;
		if (requestCode == REQUEST_MESSAGE){
			if (resultCode == AnswerRequestFragment.RESULT_ACCEPT){
				_currentRequest.setAccepted(Boolean.TRUE);
				requestAccepted(_currentView);
			}else if(resultCode == AnswerRequestFragment.RESULT_REFUSE){
				_currentRequest.setAccepted(Boolean.FALSE);
				requestRefused(_currentView);
			}
			Model.getRequestRepo().update(_currentRequest, _currentRequest.getId());
		}
		_currentRequest = null;
		_currentView = null;
	}

	ToRun getAllRequest = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			_listOfRequests = Model.getRequestRepo().selectByReceiver(ConnectedMember.getMember());
			return null;
		}
	};

	ToRun postGetAllRequest = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			setListAdapter(new RequestFragmentAdapter(_listOfRequests));
			return null;
		}
	};
}