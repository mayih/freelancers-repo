package il.bruzn.freelancers.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.Delegate;
import il.bruzn.freelancers.basic.ToRun;


public class SignInActivity extends ActionBarActivity {

	EditText	_email, _password;
	Button		_connect, _joinin;
	ProgressDialog _progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		if (Module.getMemberRepo() == null) // If the technologie hasn't been instanced
			Module.create(this, Module.DB_NAME, Module.DB_VERSION);

		// Check if the user is already connected
		_progressDialog = ProgressDialog.show(this, "Authenticating", "Loading..");

		new AsyncToRun().execute(new ToRun<ToRun>() {
			@Override
			public ToRun run(Object... parameters) {
				// on thread..
				String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, "");
				Member member = Module.getMemberRepo().selectByEmail(email);
				if (member != null){ // Check if already connected
					ConnectedMember.setMember(member);
					startActivity(new Intent(SignInActivity.this, MainActivity.class));
					finish();
				}

				// post thread..
				return closeProgressDialog;
			}
		});

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

				// Put some design
				_progressDialog = ProgressDialog.show(SignInActivity.this, "Authenticating", "Loading..");

				// Get the member by thread:

				new AsyncToRun().execute(new ToRun<ToRun>() {
					@Override
					public ToRun run(Object... parameters) {
						// on thread..
						memberOnClick = Module.getMemberRepo().selectByEmail(emailOnClick);
						if (memberOnClick != null && memberOnClick.authenticate(emailOnClick, passwordOnClick)) {
							ConnectedMember.setMember(memberOnClick);
							SharedPreferences.Editor edit = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit();
							edit.putString(ConnectedMember.key, memberOnClick.getEmail()).commit();

							startActivity(new Intent(SignInActivity.this, MainActivity.class));
							finish();
						}
						else
							Toast.makeText(getApplicationContext(), "The email or the password doesn't match with our datas", Toast.LENGTH_LONG).show();

						// post thread..
						return closeProgressDialog;
					}
				});
			}
		});
	}

	// Functions for thread
	private ToRun closeProgressDialog = new ToRun<String>() {
		@Override
		public String run(Object... parameters) {
			Member memberOnClick = null;

			if (_progressDialog.isShowing()) {
				_progressDialog.dismiss();
			}
			return null;
		}
	};
}