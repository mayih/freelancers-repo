package il.bruzn.freelancers.Controller;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.ConnectedMember;
import il.bruzn.freelancers.Modele.Modele;
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

		if (Modele.getMemberRepo() == null) // If the technologie hasn't been instanced
			Modele.create(this, Modele.DB_NAME, Modele.DB_VERSION);

		// Check if the user is already connected
		String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, "");
		if (email != null && !email.isEmpty()) { // if the file isn't empty
			_progressDialog = ProgressDialog.show(this, "Authenticating", "Loading.."); // Show progress dialog

			// Launch thread..
			new AsyncToRun<Member>()
					.setMain(autoAuthentication)
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
			startActivity(new Intent(SignInActivity.this, JoinInActivity.class), ActivityOptions.makeCustomAnimation(getApplicationContext(), R.transition.slide_right_to_left, R.transition.slide_left_to_right).toBundle());
			}
		});
		_connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_progressDialog = ProgressDialog.show(SignInActivity.this, "Authenticating", "Loading..");
				new AsyncToRun<Member>()
						.setMain(connection)
						.setPost(closeProgressDialog)
						.execute();
			}
		});
	}

	private ToRun<Member> autoAuthentication = new ToRun<Member>() {
		int param;
		@Override
		public Member run(Object... parameters) {
			String email = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).getString(ConnectedMember.key, "");
			return Modele.getMemberRepo().selectByEmail(email);
		}
	};
	private ToRun<Member> connection = new ToRun<Member>() {
		int  param;
		@Override
		public Member run(Object... parameters) {
			String email	= _email.getText().toString();
			String password	= _password.getText().toString();

			return Modele.getMemberRepo().selectByEmailAndPassword(email, password);
		}
	};
	private ToRun closeProgressDialog = new ToRun<Void>() {
		int param;
		@Override
		public Void run(Object... parameters) {
			if (_progressDialog.isShowing())
				_progressDialog.dismiss();

			if (	(parameters.length > 0) &&
					(parameters[0] != null) &&
					(parameters[0].getClass() == Member.class) ) {
				Member member = (Member)parameters[0];
				ConnectedMember.setMember(member);
				SharedPreferences.Editor edit = getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit();
				edit.putString(ConnectedMember.key, member.getEmail()).commit();

				startActivity(new Intent(SignInActivity.this, MainActivity.class));
				finish();
			}
			else
				Toast.makeText(SignInActivity.this, getResources().getText(R.string.wrong_login_message), Toast.LENGTH_SHORT).show();
			return null;
		}
	};
}