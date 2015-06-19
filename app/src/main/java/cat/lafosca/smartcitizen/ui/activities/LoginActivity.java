package cat.lafosca.smartcitizen.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cat.lafosca.smartcitizen.R;

public class LoginActivity extends AppCompatActivity {

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
        
    }

    @OnClick(R.id.forgot_password_tv)
    public void userForgotPassw() {
        String url = "http://i.imgur.com/0gUw5gH.gif";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
