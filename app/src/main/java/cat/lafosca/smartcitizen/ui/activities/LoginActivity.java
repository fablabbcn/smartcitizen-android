package cat.lafosca.smartcitizen.ui.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cat.lafosca.smartcitizen.R;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.text_input_passw)
    TextInputLayout mTextInputLayoutPassw;

    @InjectView(R.id.text_input_name)
    TextInputLayout mTextInputLayoutName;

//    @InjectView(R.id.scrollWrapper)
//    ScrollView mScrollViewImageWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        //mScrollViewImageWrapper.setEnabled(false);
//        mTextInputLayoutName = (TextInputLayout)findViewById(R.id.text_input_name);
//        mTextInputLayoutPassw = (TextInputLayout)findViewById(R.id.text_input_passw);

        //mTextInputLayoutName.getEditText().setTypeface(Typeface.SANS_SERIF);

        //EditText editText =(EditText)findViewById(R.id.insert_yors_edit_text_layout);
        /*Typeface type = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Regular.ttf");
        mTextInputLayoutName.getEditText().setTypeface(type);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* EditText etName = mTextInputLayoutName.getEditText();
        etName.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT);*/
    }
}
