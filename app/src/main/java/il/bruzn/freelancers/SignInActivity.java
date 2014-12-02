package il.bruzn.freelancers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;


import il.bruzn.freelancers.Entities.Member;
import il.bruzn.freelancers.basic.ConnectedMember;


public class SignInActivity extends ActionBarActivity {

	EditText	_email, _password;
	Button		_connect, _joinin;

	ConnectedMember _coMember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, null);
		Member member = selectByEmail(email, _coMember.MembersArray);
		if (member != null){ // Check if already connected
			ConnectedMember.setMember(member);
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}

		setContentView(R.layout.activity_sign_in);
		_email		= (EditText)findViewById(R.id.email);
		_password	= (EditText)findViewById(R.id.password);
		_connect	= (Button)	findViewById(R.id.connect_button);
		_joinin		= (Button)	findViewById(R.id.joinin_button);

		_joinin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			startActivity(new Intent(SignInActivity.this, JoinInActivity.class));
			}
		});
		_connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			// Check email and password
			String email = _email.getText().toString(), password = _password.getText().toString();
			Member member = selectByEmail(email, _coMember.MembersArray);
			if (member != null && member.authenticate(email, password)) {
				ConnectedMember.setMember(member);
				SharedPreferences.Editor edit = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit();
				edit.putString(ConnectedMember.key, member.getEmail()).commit();

				startActivity(new Intent(SignInActivity.this, MainActivity.class));
				finish();
			}
			else
				Toast.makeText(getApplicationContext(), "The email or the password doesn't match with our datas", Toast.LENGTH_LONG).show();
			}
		});
	}

	private Member selectByEmail(String email, ArrayList<Member> members){
		for (Member member:members){
			if (member.getEmail().equals(email))
				return member;
		}
		return null;
	}
}
