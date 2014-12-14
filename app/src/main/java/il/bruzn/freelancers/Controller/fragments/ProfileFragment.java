package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
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
public class ProfileFragment  extends Fragment {
    public static final String EMAIL_MEMBER_KEY ="member's email";
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        String memberEmail = (String)getArguments().getString(EMAIL_MEMBER_KEY);
        _member = Module.getMemberRepo().selectByEmail(memberEmail);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        _picture = (ImageView)layout.findViewById(R.id.profile_picture);


        _firstName = (TextView)layout.findViewById(R.id.profile_firstName_label);
        _firstName.setText(_member.getFirstName());

        _lastName = (TextView)layout.findViewById(R.id.profile_lastName_label);
        _lastName.setText(_member.getLastName());

        _specialisation = (TextView)layout.findViewById(R.id.profile_specialisation_label);

        _email = (TextView)layout.findViewById(R.id.profile_email_label);
        _email.setText(_member.getEmail());

        _phoneNumber = (TextView)layout.findViewById(R.id.profile_phoneNumber_label);
        _phoneNumber.setText(_member.getPhoneNumber());

        _adress = (TextView)layout.findViewById(R.id.profile_adress_label);

		return layout;
	}
}
