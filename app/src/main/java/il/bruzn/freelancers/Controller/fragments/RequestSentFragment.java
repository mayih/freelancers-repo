package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
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
import il.bruzn.freelancers.Model.Entities.Opinion;
import il.bruzn.freelancers.Model.Entities.Request;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.Model.SqLiteTech.OpinionRepoSQLite;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ImageHelper;
import il.bruzn.freelancers.basic.ToRun;

/**
 * Created by Moshe on 26/01/15.
 */
public class RequestSentFragment extends ListFragment implements TitledFragment {

	ArrayList<Request> _listOfRequests;

	public static final int REQUEST_OPINION = 2;
	public static final String DIALOG_OPINION = "opinion";
	private Request _currentRequest;
	private Member _currentReceiver;
	private View _currentView;
	private Opinion _opinion;

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
		return "Requests sent";
	}

	class RequestSentFragmentAdapter extends ArrayAdapter<Request> {

		RequestSentFragmentAdapter(List<Request> list) {
			super(getActivity(), 0, list);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_request, parent, false);

			// Identify the interlocutor
			final Member receiver = getItem(position).getReceiver();

			// Fill convertView
			TextView nameView = (TextView) convertView.findViewById(R.id.item_request_name);
			ImageView pictureView = (ImageView) convertView.findViewById(R.id.request_profile_pic);
			Bitmap picture;
			TextView messageView = (TextView) convertView.findViewById(R.id.request_message_text);
			TextView status = (TextView) convertView.findViewById(R.id.request_message_status);
			Button opinionButton = (Button) convertView.findViewById(R.id.request_opinion_button);

			nameView.setText(receiver.getFirstName() + " " + receiver.getLastName());
			messageView.setText(getItem(position).getText());

			opinionButton.setVisibility(View.INVISIBLE);
			opinionButton.setEnabled(false);

			if (getItem(position).getisDone()){
				status.setText("Is done");
				if (getItem(position).getOpinion() == null) {
					opinionButton.setVisibility(View.VISIBLE);
					opinionButton.setEnabled(true);
					final View finalConvertView = convertView;
					opinionButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							_currentReceiver = receiver;
							_currentRequest = getItem(position);
							_currentView = finalConvertView;
							FragmentManager fm = getActivity().getSupportFragmentManager();
							EditTextDialogFragment dialog = EditTextDialogFragment.newInstance("Opinion");
							dialog.setTargetFragment(RequestSentFragment.this, REQUEST_OPINION);
							dialog.show(fm, DIALOG_OPINION);
						}
					});
				}
			}else if (getItem(position).getIsAccepted() == Boolean.FALSE)
			{
				status.setText("Refused");
			}else if (getItem(position).getIsAccepted() == Boolean.TRUE)
			{
				status.setText("Accepted");
			}else if (getItem(position).getIsAccepted() == null)
			{
				status.setText("In Progress..");
			}



			if (receiver.getPicture() != null)
				picture = receiver.getPicture();
			else
				picture = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
			picture = ImageHelper.getRoundedCornerBitmap(picture, 100);
			pictureView.setImageBitmap(picture);

			return convertView;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_OPINION){
			String message = (String)data.getSerializableExtra(EditTextDialogFragment.EXTRA_MESSAGE);
			double level = (double)data.getSerializableExtra(EditTextDialogFragment.EXTRA_LEVEL);
			_opinion = new Opinion(ConnectedMember.getMember(), _currentReceiver, level, message, _currentRequest.getId());
			new AsyncToRun<>()
					.setMain(addOpinion)
					.setPost(postAddOpinion)
					.execute();
			_currentView.findViewById(R.id.request_opinion_button).setVisibility(View.INVISIBLE);
			_currentView.findViewById(R.id.request_opinion_button).setEnabled(false);
		}
		_currentView = null;
		_currentReceiver = null;
	}

	private ToRun addOpinion = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			Model.getOpnionRepo().add(_opinion);
			ArrayList<Opinion> opinions = Model.getOpnionRepo().selectBy(OpinionRepoSQLite.FIELDS_NAME.REQUEST_ID.toString(), _currentRequest.getId()+"");
			_currentRequest.setOpinion(opinions.get(0));
			Model.getRequestRepo().update(_currentRequest, _currentRequest.getId());
			return null;
		}
	};

	private ToRun postAddOpinion = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			_opinion = null;
			_currentRequest = null;
			return null;
		}

	};

	private ToRun getAllRequest = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			_listOfRequests = Model.getRequestRepo().selectFinishedRequest(ConnectedMember.getMember());
			return null;
		}
	};

	private ToRun postGetAllRequest = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			setListAdapter(new RequestSentFragmentAdapter(_listOfRequests));
			return null;
		}
	};
}