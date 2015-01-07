package il.bruzn.freelancers.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Model;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ToRun;

public class SignInActivity extends ActionBarActivity {

	EditText	_email, _password;
	Button		_connect, _joinin;
	ProgressDialog _progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		if (Model.getMemberRepo() == null) // If the technologie hasn't been instanced
			Model.create(this, Model.DB_NAME, Model.DB_VERSION);

		// Check if the user is already connected
		String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, "");

		if (email != null && !email.isEmpty()) { // if the file isn't empty
			_progressDialog = ProgressDialog.show(this, "Authenticating", "Loading.."); // Show progress dialog

			// Launch thread..
			new AsyncToRun<String>()
					.setMain(requestTheMember)
					.setPost(closeProgressDialog)
					.execute();
		}

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

			@Override
			public void onClick(View v) {
				// Put some design
				_progressDialog = ProgressDialog.show(SignInActivity.this, "Authenticating", "Loading..");

				new AsyncToRun<String>()
						.setMain(connection)
						.setPost(closeProgressDialog)
						.execute();
			}
		});
	}

	ToRun<String> requestTheMember = new ToRun<String>()
	{
		@Override
		public String run(Object... parameters) { // Setup the function for thread
			// on thread..
			String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, "");
			Member member = Model.getMemberRepo().selectByEmail(email);
			if (member != null){ // Check if already connected
				ConnectedMember.setMember(member);
				startActivity(new Intent(SignInActivity.this, MainActivity.class));
				finish();
			}
			// post thread..
			return null;
		}
	};

	ToRun<String> connection = new ToRun<String>() {
		@Override
		public String run(Object... parameters) {
			String finalMessage = null;
			Member memberOnClick = null;
			String emailOnClick, passwordOnClick;

			// Check email and password
			emailOnClick	= _email.getText().toString();
			passwordOnClick	= _password.getText().toString();
			// on thread..
			memberOnClick = Model.getMemberRepo().selectByEmail(emailOnClick);
			if (memberOnClick != null && memberOnClick.authenticate(emailOnClick, passwordOnClick)) {
				ConnectedMember.setMember(memberOnClick);
				SharedPreferences.Editor edit = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit();
				edit.putString(ConnectedMember.key, memberOnClick.getEmail()).commit();

				startActivity(new Intent(SignInActivity.this, MainActivity.class));
				finish();
			}
			else
				finalMessage = "The email or the password doesn't match with our datas";

			// post thread..
			return finalMessage;
		}
	};

	private ToRun closeProgressDialog = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			if (	(parameters.length > 0) &&
					(parameters[0] != null) &&
					(parameters[0].getClass() == String.class) &&
					(!((String)parameters[0]).isEmpty()) ) {
				Toast.makeText(SignInActivity.this, (String) parameters[0], Toast.LENGTH_LONG).show();
			}

			if (_progressDialog.isShowing())
				_progressDialog.dismiss();
			return null;
		}
	};
}