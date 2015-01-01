package il.bruzn.freelancers.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.lang.reflect.Method;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncDelegate;
import il.bruzn.freelancers.basic.Delegate;


public class SignInActivity extends ActionBarActivity {

	EditText	_email, _password;
	Button		_connect, _joinin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Module.getMemberRepo() == null) // If the technologie hasn't been instanced
			Module.create(this, Module.DB_NAME, Module.DB_VERSION);

		// Check if the user is already connected
		try {
			new AsyncDelegate().execute(new Delegate(this, "getStoredMember"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		setContentView(R.layout.activity_sign_in);
		_email		= (EditText)findViewById(R.id.email);
		_password	= (EditText)findViewById(R.id.password);
		_connect	= (Button)	findViewById(R.id.connect_button);
		_joinin		= (Button)	findViewById(R.id.joinin_button);

		_joinin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			startActivity(new Intent(SignInActivity.this, JoinInActivity.class)/*, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.transition.slide_right_to_left, R.transition.slide_left_to_right).toBundle()*/);
			}
		});
		_connect.setOnClickListener(new View.OnClickListener() {
			Member memberOnClick = null;
			String emailOnClick, passwordOnClick;
			@Override
			public void onClick(View v) {
				// Check email and password
				emailOnClick	= _email.getText().toString();
				passwordOnClick	= _password.getText().toString();
				
				// Get the member by thread:
				try {
					Delegate<Member> getMemberToConnect = new Delegate<Member>(this, "getMemberToConnect", emailOnClick);
					AsyncDelegate thread = new AsyncDelegate();
					thread.execute(getMemberToConnect);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}

			// Function for thread
			public void getMemberToConnect(String email){
				memberOnClick = Module.getMemberRepo().selectByEmail(email);
				if (memberOnClick != null && memberOnClick.authenticate(emailOnClick, passwordOnClick)) {
					ConnectedMember.setMember(memberOnClick);
					SharedPreferences.Editor edit = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit();
					edit.putString(ConnectedMember.key, memberOnClick.getEmail()).commit();

					startActivity(new Intent(SignInActivity.this, MainActivity.class));
					finish();
				}
				else
					Toast.makeText(getApplicationContext(), "The email or the password doesn't match with our datas", Toast.LENGTH_LONG).show();
			}

		});
	}

	// Function for thread
	public void getStoredMember() {
		String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, "");
		Member member = Module.getMemberRepo().selectByEmail(email);
		if (member != null){ // Check if already connected
			ConnectedMember.setMember(member);
			startActivity(new Intent(this, MainActivity.class));
			finish();
			return;
		}
	}
}
