package com.updateplease.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.updateplease.R;
import com.updateplease.helper.Functions;

public class SignupActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.edt_first_name)
  EditText mEdtFirstName;
  @BindView(R.id.edt_last_name)
  EditText mEdtLastName;
  @BindView(R.id.edt_mobile)
  EditText mEdtMobile;
  @BindView(R.id.edt_email)
  EditText mEdtEmail;
  @BindView(R.id.button_contine)
  Button mButtonContine;
  @BindView(R.id.txt_first_name)
  TextView mTxtFirstName;
  @BindView(R.id.txt_last_name)
  TextView mTxtLastName;
  @BindView(R.id.txt_mobile)
  TextView mTxtMobile;
  @BindView(R.id.txt_email)
  TextView mTxtEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    ButterKnife.bind(this);


    setSupportActionBar(toolbar);

    getSupportActionBar().setTitle(R.string.sign_up);

    // add back arrow to toolbar
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }




  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // handle arrow click here
    if (item.getItemId() == android.R.id.home) {
      finish(); // close this activity and return to preview activity (if there is any)
    }

    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.button_contine)
  public void onViewClicked() {

    // Hide Keyboard
    Functions.hideSoftKeyboard(SignupActivity.this);

    String email = mEdtEmail.getText().toString().trim();

    // Check for empty data in the form
    if (!email.isEmpty()) {
      if (Functions.isValidEmailAddress(email)) {
        // login user
        //loginProcess(email, password);
      } else {
        Toast.makeText(getApplicationContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
      }
    } else {
      // Prompt user to enter credentials
      Toast.makeText(getApplicationContext(), "Please enter the values!", Toast.LENGTH_LONG).show();
    }
  



  }
}
