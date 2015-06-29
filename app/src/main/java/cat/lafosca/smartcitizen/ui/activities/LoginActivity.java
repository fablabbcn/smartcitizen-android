package cat.lafosca.smartcitizen.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;
import cat.lafosca.smartcitizen.controllers.SessionController;
import retrofit.RetrofitError;

public class LoginActivity extends AppCompatActivity implements SessionController.SessionControllerListener {

    public static final int LOGIN_OK = 1001;

    @InjectView(R.id.text_input_passw)
    TextInputLayout mTextInputLayoutPassw;

    @InjectView(R.id.text_input_name)
    TextInputLayout mTextInputLayoutName;

    @InjectView(R.id.forgot_password_tv)
    TextView mTvForgotPassw;

    @InjectView(R.id.progress_login)
    ProgressBar mProgress;

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
        String url = "https://www.google.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.login_button)
    public void login() {
        String name = mTextInputLayoutName.getEditText().getText().toString();
        String passw = mTextInputLayoutPassw.getEditText().getText().toString();

        if ( name.length() > 0 && passw.length() > 0) {
            mProgress.setVisibility(View.VISIBLE);
            SessionController.userWantsToLogin(this, name, passw);

        } else {
            Toast.makeText(this, getString(R.string.incorrect_login_format), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginSucces() {
        mProgress.setVisibility(View.GONE);
        setResult(LoginActivity.LOGIN_OK);
        finish();
        Toast.makeText(this, "login Success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginError(RetrofitError error) {
        mProgress.setVisibility(View.GONE);
        if (error.getResponse()!= null) {
            int status = error.getResponse().getStatus();

            //wrong username    / wrong passw -> 404
            //wrong username    / correct passw -> 404
            //correct username  / wrong passw -> 422

            if (status == 422 || status == 404) {
                Toast.makeText(this, getString(R.string.incorrect_login), Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_LONG).show();
    }
}
