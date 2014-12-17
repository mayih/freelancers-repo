package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 01/12/2014.
 */
public class ProfileFragment extends Fragment  implements TitledFragment {
	public static final String MEMBER_KEY ="member's email";

	private Member _member;

    private ImageView _picture;
	private TextView _firstName;
    private TextView _lastName;
    private TextView _specialisation;
    private TextView _email;
    private TextView _phoneNumber;
    private TextView _adress;
    private TextView _average;

	public ProfileFragment() {
		_member = ConnectedMember.getMember();
	}

	@Override
	public String getTitle() {
		return _member.getFirstName()+" "+_member.getLastName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			_member = (Member)Module.getHashMap().get(getArguments().getLong(MEMBER_KEY));
			Module.getHashMap().remove(MEMBER_KEY);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        _picture = (ImageView)v.findViewById(R.id.profile_picture);


        _firstName = (TextView)v.findViewById(R.id.profile_firstName_label);
        _firstName.setText(_member.getFirstName());

        _lastName = (TextView)v.findViewById(R.id.profile_lastName_label);
        _lastName.setText(_member.getLastName());

        _specialisation = (TextView)v.findViewById(R.id.profile_specialisation_label);

        _email = (TextView)v.findViewById(R.id.profile_email_label);
        _email.setText(_member.getEmail());

        _phoneNumber = (TextView)v.findViewById(R.id.profile_phoneNumber_label);
        _phoneNumber.setText(_member.getPhoneNumber());

        _adress = (TextView)v.findViewById(R.id.profile_adress_label);

        _average = (TextView)v.findViewById(R.id.profile_average_label);
        if(_member.getAverage() != 0) {
            _average.setText(_member.getAverage() + " / 5");
            _average.setVisibility(View.VISIBLE);
        }

		return v;
	}
}
