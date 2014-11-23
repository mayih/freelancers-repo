package il.bruzn.freelancers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class SignInActivity extends ActionBarActivity {

	EditText	_email, _password;
	Button		_connect, _joinin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (false){ //Check if already connected
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		}

		setContentView(R.layout.activity_sign_in);
		_email		= (EditText) findViewById(R.id.email);
		_password	= (EditText) findViewById(R.id.password);
		_connect	= (Button) findViewById(R.id.connect_button);
		_joinin		= (Button) findViewById(R.id.joinin_button);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
