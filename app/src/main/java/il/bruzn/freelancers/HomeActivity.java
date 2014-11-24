package il.bruzn.freelancers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import il.bruzn.freelancers.basic.ConnectedMember;


public class HomeActivity extends ActionBarActivity {

	Button _diesconnect;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		_diesconnect = (Button)findViewById(R.id.disconnect_button);

		TextView welcome = (TextView)findViewById(R.id.welcometext);
		welcome.setText("Welcome "+ ConnectedMember.getMember().getEmail());

		_diesconnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectedMember.nullMember();
				getSharedPreferences(ConnectedMember.filename, MODE_PRIVATE).edit().clear().commit();
				startActivity(new Intent(getApplicationContext(), SignInActivity.class));
				finish();
			}
		});
	}
}
