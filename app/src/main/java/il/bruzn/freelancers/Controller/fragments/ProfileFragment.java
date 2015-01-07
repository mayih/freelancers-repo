package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Model;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 01/12/2014.
 */
public class ProfileFragment extends Fragment  implements TitledFragment {
	public static final String MEMBER_KEY ="member's email";

	private Member _member;
	private boolean _IsMyProfile;

    private ImageView _picture;
	private TextView _firstName;
    private TextView _lastName;
    private TextView _speciality;
    private TextView _email;
    private TextView _phoneNumber;
    private TextView _adress;
    private TextView _average;
	private Button _reqButton;

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
			_IsMyProfile = false;
		}
		else {
			_member = ConnectedMember.getMember();
			_IsMyProfile = true;
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

		if(_IsMyProfile) {
			_reqButton.setText(R.string.modify_button);
			_reqButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity)getActivity()).setFragment(new EditMyProfileFragment());
				}
			});
		}else{
			_reqButton.setText(R.string.request_button);
			_reqButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Intent i = new Intent(getActivity(), );
					//startActivity(i);
				}
			});
		}
		return v;
	}
}