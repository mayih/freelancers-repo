package il.bruzn.freelancers;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class JoinInActivity extends ActionBarActivity {

    private EditText _email;
    private EditText _lastName;
    private EditText _firstName;
    private EditText _password;
    private EditText _repeatPassword;
    private String _emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button _joinIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_in);

        _email = (EditText)findViewById(R.id.email_edtext);
        _lastName = (EditText)findViewById(R.id.lastname_edtext);
        _firstName = (EditText)findViewById(R.id.firstname_edtext);
        _password = (EditText)findViewById(R.id.password_edtext);
        _repeatPassword = (EditText)findViewById(R.id.repeatpass_edtext);

        _joinIn = (Button)findViewById(R.id.joinin_button);
        _joinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((_email.getText().toString().matches(_emailPattern))||
                   (_email.getText().toString().isEmpty())){


                }else if(_lastName.getText().toString().isEmpty()){


                }else if(_firstName.getText().toString().isEmpty()){


                }else if ((_password.getText().toString().isEmpty()) ||
                        (_password.getText().toString() != _repeatPassword.getText().toString())){


                }else {
                    //comfirme
                }
            }
        });


    }

}
