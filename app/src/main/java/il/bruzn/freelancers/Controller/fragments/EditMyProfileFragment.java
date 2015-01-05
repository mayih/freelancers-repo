package il.bruzn.freelancers.Controller.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import il.bruzn.freelancers.Modele.ConnectedMember;
import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.Modele;
import il.bruzn.freelancers.R;

/**
 * Created by Moshe on 04/01/15.
 */
public class EditMyProfileFragment extends Fragment implements TitledFragment{
	private Member _member;

	private ImageView _picture;
	private EditText _firstName;
	private EditText _lastName;
	private EditText _speciality;
	private EditText _email;
	private EditText _phoneNumber;
	private EditText _adress;
	private Button _reqButton;

	@Override
	public String getTitle() {
		return _member.getFirstName()+" "+_member.getLastName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			_member = ConnectedMember.getMember();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_edit_my_profile, container, false);
		_picture = (ImageView)v.findViewById(R.id.editProfile_picture);
		if (_member.getPicture() != null)
			_picture.setImageBitmap(_member.getPicture());

		_firstName = (EditText)v.findViewById(R.id.editProfile_firstName_label);
		_firstName.setText(_member.getFirstName());

		_lastName = (EditText)v.findViewById(R.id.editProfile_lastName_label);
		_lastName.setText(_member.getLastName());

		_speciality = (EditText)v.findViewById(R.id.editProfile_specialisation_label);
		_speciality.setText(_member.getSpeciality());

		_email = (EditText)v.findViewById(R.id.editProfile_email_label);
		_email.setText(_member.getEmail());

		_phoneNumber = (EditText)v.findViewById(R.id.editProfile_phoneNumber_label);
		_phoneNumber.setText(_member.getPhoneNumber());

		_adress = (EditText)v.findViewById(R.id.editProfile_adress_label);
		_adress.setText(_member.getAddress().toString());


		_reqButton = (Button)v.findViewById(R.id.recordRequest_button);
		_reqButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Member member = _member.setEmail(_email.getText().toString())
						.setFirstName(_firstName.getText().toString())
						.setLastName(_lastName.getText().toString());
				Modele.getMemberRepo().update(member, member.getId());

				((HomeFragment)MenuFragment.getMenu()[0].getFragment()).isToUpdate();
			}
		});

		return v;
	}
}
