package il.bruzn.freelancers.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.Modele;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ToRun;


public class JoinInActivity extends ActionBarActivity {
	ProgressDialog _progressDialog;
    private EditText _email;
    private final String _emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText _lastName;
    private EditText _firstName;
	private Spinner _specialities;
    private final String _namePattern = "[a-zA-Z]+";
    private EditText _password;
    private EditText _repeatPassword;
    private Button _joinIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_in);

        _email = (EditText)findViewById(R.id.email_edtext); //field email
        editTextValid(_email, _emailPattern);

        _firstName = (EditText)findViewById(R.id.firstname_edtext); //field firstname
        editTextValid(_firstName, _namePattern);

        _lastName = (EditText)findViewById(R.id.lastname_edtext); //field lastName
        editTextValid(_lastName, _namePattern);

		_specialities = (Spinner)findViewById(R.id.speciality_spinner);

        _password = (EditText)findViewById(R.id.password_edtext); //field password
        _repeatPassword = (EditText)findViewById(R.id.repeatpass_edtext);//field rpassword

        _joinIn = (Button)findViewById(R.id.joinin_button);//Button Join-in


        //#####-- Click Join-In --#####
        _joinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				_progressDialog = ProgressDialog.show(JoinInActivity.this, "Checking", "Loading.."); // Show progress dialog

			new AsyncToRun<Member>()
					.setMain(addMember)
					.setPost(printIsMemberExist).execute();

            }
        });
    }

    private void editTextValid(final EditText e, final String Pattern){

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!e.getText().toString().matches(Pattern) && e.getText().toString().length() != 0){
                    e.setTextColor(Color.RED);
                }
                else  e.setTextColor(Color.BLACK);
            }
        });
    }

	ToRun<Member> addMember = new ToRun<Member>() {
		@Override
		public Member run(Object... parameters) {
			Member IsMemberExist = Modele.getMemberRepo().selectByEmail(_email.getText().toString());

			if (IsMemberExist == null) {
				if ((!_email.getText().toString().matches(_emailPattern)) || //if is not a mail or
						(_email.getText().toString().isEmpty())) {
				} else if (_firstName.getText().toString().isEmpty()) {

				} else if (_lastName.getText().toString().isEmpty()) {

				} else if (_specialities.getSelectedItem().toString() == "N/S") {

				} else if ((_password.getText().toString().isEmpty()) ||
						(!_password.getText().toString().equals(_repeatPassword.getText().toString()))) {

				} else {
					Member member = new Member().setEmail(_email.getText().toString())
							.setPassword(_password.getText().toString())
							.setFirstName(_firstName.getText().toString())
							.setLastName(_lastName.getText().toString())
							.setSpeciality(_specialities.getSelectedItem().toString());

					Modele.getMemberRepo().add(member);
				}
			}
			return IsMemberExist;
		}
	};

	ToRun printIsMemberExist = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {

			if (_progressDialog.isShowing())
				_progressDialog.dismiss();

			if (	(parameters.length > 0) &&
					(parameters[0] != null) &&
					(parameters[0].getClass() == Member.class)) {
				Toast.makeText(JoinInActivity.this,"Existe Déjà", Toast.LENGTH_LONG).show();
			}
			else
			{
				// go Sign In activity
				startActivity(new Intent(JoinInActivity.this, SignInActivity.class));
				finish();
			}

			return null;
		}
	};

}
