package cat.lafosca.smartcitizen.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.controllers.SessionController;

public class LoginActivity extends AppCompatActivity implements SessionController.SessionControllerListener {

    @InjectView(R.id.text_input_passw)
    TextInputLayout mTextInputLayoutPassw;

    @InjectView(R.id.text_input_name)
    TextInputLayout mTextInputLayoutName;

    @InjectView(R.id.forgot_password_tv)
    TextView mTvForgotPassw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.forgot_password_tv)
    public void userForgotPassw() {
        String url = "http://i.imgur.com/0gUw5gH.gif";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.login_button)
    public void login() {
        String name = mTextInputLayoutName.getEditText().getText().toString();
        String passw = mTextInputLayoutPassw.getEditText().getText().toString();

        if ( name.length() > 0 && passw.length() > 0) {
            SessionController.userWantsToLogin(this, name, passw);

        } else {
            Toast.makeText(this, getString(R.string.incorrect_login_format), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginSucces() {
        Toast.makeText(this, "login Success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginError() {
        Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_LONG).show();
    }
}
