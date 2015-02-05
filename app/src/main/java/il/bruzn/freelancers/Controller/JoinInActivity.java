package il.bruzn.freelancers.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.AsyncToRun;
import il.bruzn.freelancers.basic.ToRun;


public class JoinInActivity extends ActionBarActivity {

	static final String myUrl = "http://147.161.46.230:8080/Freelancer/api";

	private ProgressDialog _progressDialog;
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
		new AsyncToRun<String[]>()
				.setMain(mainFillSpecialities)
				.setPost(postFillSpecialities).execute();

		_password = (EditText)findViewById(R.id.password_edtext); //field password
		_repeatPassword = (EditText)findViewById(R.id.repeatpass_edtext);//field rpassword

		_joinIn = (Button)findViewById(R.id.joinin_button);//Button Join-in


		//#####-- Click Join-In --#####
		_joinIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_progressDialog = ProgressDialog.show(JoinInActivity.this, "Checking", "Loading.."); // Show progress dialog

				new AsyncToRun<String>()
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

	private ToRun<String> addMember = new ToRun<String>() {
		@Override
		public String run(Object... parameters) {
			Member IsMemberExist = Model.getMemberRepo().selectByEmail(_email.getText().toString());
			String msg = "Exist";

			if (IsMemberExist == null) {
				if ((!_email.getText().toString().matches(_emailPattern)) || //if is not a mail or
						(_email.getText().toString().isEmpty())) {
					msg = "mail error or empty";
				} else if (_firstName.getText().toString().isEmpty()) {
					msg = "first name empty";
				} else if (_lastName.getText().toString().isEmpty()) {
					msg = "last name empty";
				} else if (_specialities.getSelectedItem().toString() == "N/S") {
					msg = "speciality empty";
				} else if ((_password.getText().toString().isEmpty())){
					msg = "password empty";
				} else if ((!_password.getText().toString().equals(_repeatPassword.getText().toString()))){
					msg = "passwords not equals";
				}
				else {
					msg = "not exist";
					Member member = new Member().setEmail(_email.getText().toString())
							.setPassword(_password.getText().toString())
							.setFirstName(_firstName.getText().toString())
							.setLastName(_lastName.getText().toString())
							.setSpeciality(_specialities.getSelectedItem().toString());

					Model.getMemberRepo().add(member);
				}
			}
			return msg;
		}
	};

	private ToRun printIsMemberExist = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {

			if (_progressDialog.isShowing())
				_progressDialog.dismiss();

			if (	(parameters.length > 0) &&
					(parameters[0] != null) &&
					(parameters[0] != "not exist") &&
					(parameters[0].getClass() == String.class)) {
				Toast.makeText(JoinInActivity.this,(String)parameters[0], Toast.LENGTH_LONG).show();
			}else{
				// go Sign In activity
				startActivity(new Intent(JoinInActivity.this, SignInActivity.class));
				finish();
			}
			return null;
		}
	};

	private ToRun<String[]> mainFillSpecialities = new ToRun<String[]>() {
		@Override
		public String[] run(Object... parameters) {


//			String result = new String("[\"N/S\", \"Teacher\", \"Computer\"]");
			String [] specialitysArray = new String[]{};
			try {
				URL url = new URL(myUrl);

				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.connect();
				InputStream inputStream = connection.getInputStream();
				String result = convertIputStreamToString(inputStream);

				JSONArray jsonArray = new JSONArray(result);
				specialitysArray = new String[jsonArray.length()];

				for (int i = 0; i < jsonArray.length(); i++){
					specialitysArray[i] = jsonArray.getString(i);
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch (IOException e){
				e.printStackTrace();
			}

			return specialitysArray;
		}
	};
	private	ToRun postFillSpecialities = new ToRun<Void>() {
		@Override
		public Void run(Object... parameters) {
			if (parameters.length > 0 &&
					parameters[0] != null &&
					parameters[0].getClass() == String[].class) {
				String [] specialitysArray = (String[])parameters[0];
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(JoinInActivity.this, android.R.layout.simple_spinner_item, specialitysArray);
				_specialities.setAdapter(adapter);
			}
			return null;
		}
	};

	private String convertIputStreamToString(InputStream inputStream)throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null){
			result += line;
		}
		inputStream.close();
		return result;
	}

}