package il.bruzn.freelancers.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.R;


public class JoinInActivity extends ActionBarActivity {

    private EditText _email;
    private final String _emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText _lastName;
    private EditText _firstName;
    private final String _namePattern = "[a-zA-Z]+";
    private EditText _password;
    private EditText _repeatPassword;
    private Button _joinIn;

    private ConnectedMember _coMember;

    private void editTextValid(final EditText e, final String Pattern){

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!e.getText().toString().matches(Pattern) && e.getText().toString().length() != 0){
                    e.setTextColor(Color.RED);

                }
                else  e.setTextColor(Color.BLACK);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_in);

        _email = (EditText)findViewById(R.id.email_edtext);
        editTextValid(_email, _emailPattern);

        _lastName = (EditText)findViewById(R.id.lastname_edtext);
        editTextValid(_lastName, _namePattern);


        _firstName = (EditText)findViewById(R.id.firstname_edtext);
        editTextValid(_firstName, _namePattern);

        _password = (EditText)findViewById(R.id.password_edtext);
        _repeatPassword = (EditText)findViewById(R.id.repeatpass_edtext);

        _joinIn = (Button)findViewById(R.id.joinin_button);
        _joinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!memberIsExist(_email.getText().toString())) {

                    if ((!_email.getText().toString().matches(_emailPattern)) ||
                            (_email.getText().length() == 0)) {
                        Toast.makeText(getApplicationContext(), "e", Toast.LENGTH_LONG).show();

                    } else if (_lastName.getText().length() == 0) {

                        Toast.makeText(getApplicationContext(), "l", Toast.LENGTH_LONG).show();
                    } else if (_firstName.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "f", Toast.LENGTH_LONG).show();

                    } else if ((_password.getText().length() == 0) ||
                            !_password.getText().toString().equals(_repeatPassword.getText().toString()) ) {

                    } else {
                            Member member = new Member().setEmail(_email.getText().toString()).setPassword(_password.getText().toString());
                            _coMember.MembersArray.add(member);

                            Intent i = new Intent(JoinInActivity.this, SignInActivity.class);
                            startActivity(i);
                    }

                }else
                    Toast.makeText(getApplicationContext(), "Existe déjà", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean memberIsExist(String email)
    {
        for (Member m : _coMember.MembersArray){
            if(email.equals(m.getEmail())){
                return true;
            }
        }
        return false;
    }


}
