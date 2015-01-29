package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Model.ConnectedMember;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Request;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ToRun;

/**
 * Created by Yair on 01/12/2014.
 */
public class ProfileFragment extends Fragment  implements TitledFragment {
	public static final int REQUEST_ISSEND = 0;

	public static final String MEMBER_KEY = "member's email";
	public static final String DIALOG_REQUEST = "request";

	private Member _member;
	private boolean _isMyProfile;

	private ImageView _picture;
	private TextView _firstName;
	private TextView _lastName;
	private TextView _speciality;
	private TextView _email;
	private TextView _phoneNumber;
	private TextView _adress;
	private TextView _average;
	private Button _reqButton;
	private Request _request;

	@Override
	public String getTitle() {
		return _member.getFirstName()+" "+_member.getLastName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			_member = (Member) Model.getHashMap().get(getArguments().getLong(MEMBER_KEY));
			Model.getHashMap().remove(MEMBER_KEY);
			_isMyProfile = _member.getId() == ConnectedMember.getMember().getId(); // _isMyProfile = true;
		}
		else {
			_member = ConnectedMember.getMember();
			_isMyProfile = true;
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_profile, container, false);
		_picture = (ImageView)v.findViewById(R.id.profile_picture);
		if (_member.getPicture() != null)
			_picture.setImageBitmap(_member.getPicture());

		_firstName = (TextView)v.findViewById(R.id.profile_firstName_label);
		_firstName.setText(_member.getFirstName());

		_lastName = (TextView)v.findViewById(R.id.profile_lastName_label);
		_lastName.setText(_member.getLastName());

		_speciality = (TextView)v.findViewById(R.id.profile_specialisation_label);
		_speciality.setText(_member.getSpeciality());

		_email = (TextView)v.findViewById(R.id.profile_email_label);
		_email.setText(_member.getEmail());

		_phoneNumber = (TextView)v.findViewById(R.id.profile_phoneNumber_label);
		_phoneNumber.setText(_member.getPhoneNumber());

		_adress = (TextView)v.findViewById(R.id.profile_adress_label);
		_adress.setText(_member.getAddress().toString());

		_average = (TextView)v.findViewById(R.id.profile_average_label);
		if(_member.getAverage() != 0) {
			_average.setText(_member.getAverage() + " / 5");
			_average.setVisibility(View.VISIBLE);
		}

		_reqButton = (Button)v.findViewById(R.id.request_button);

		if(_isMyProfile) {
			_reqButton.setText(R.string.modify_button);
			_reqButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity)getActivity()).setFragment(new EditMyProfileFragment());
				}
			});
		}else{
			_reqButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					FragmentManager fm = getActivity().getSupportFragmentManager();
					EditTextDialogFragment dialog = EditTextDialogFragment.newInstance("Request");
					dialog.setTargetFragment(ProfileFragment.this, REQUEST_ISSEND);
					dialog.show(fm, DIALOG_REQUEST);
				}
			});
			new AsyncToRun<Request>()
					.setMain(mainRequestInProgress)
					.setPost(postRequestInProgress).execute();
		}

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_ISSEND){
			String message = (String)data.getSerializableExtra(EditTextDialogFragment.EXTRA_MESSAGE);
			Request request = new Request(ConnectedMember.getMember(), _member, message);
			new AsyncToRun<Request>()
					.setMain(addResquest)
					.setPost(null)
					.execute(request);
			_reqButton.setText("In Progress...");
			_reqButton.setEnabled(false);
		}
	}

	private ToRun<Request> addResquest = new ToRun<Request>() {
		@Override
		public Request run(Object... parameters) {
			if (parameters.length > 0 &&
					parameters[0] != null &&
					parameters[0].getClass() == Request.class){
				Model.getRequestRepo().add((Request)parameters[0]);
			}
			return null;
		}
	};

	private ToRun<Request> mainRequestInProgress = new ToRun<Request>() {
		@Override
		public Request run(Object... parameters) {
			return 	Model.getRequestRepo().selectLastInProgress(ConnectedMember.getMember(), _member);
		}
	};

	private ToRun postRequestInProgress = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {

			if (parameters.length > 0 &&
					parameters[0] != null &&
					parameters[0].getClass() == Request.class)
			{
				_reqButton.setText("In Progress...");
				_reqButton.setEnabled(false);
			}else {
				_reqButton.setText(R.string.request_button);
			}

			return null;
		}
	};
}