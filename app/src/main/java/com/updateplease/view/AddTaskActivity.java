package com.updateplease.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.updateplease.R;
import com.updateplease.helper.Functions;
import com.updateplease.presenter.AddTaskPresenter;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements AddTaskMvpView {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.txt_who)
  TextView mTxtWho;
  @BindView(R.id.edt_first_name)
  EditText mEdtFirstName;
  @BindView(R.id.edt_what)
  EditText mEdtWhat;
  @BindView(R.id.txt_remaind_from)
  TextView mTxtRemaindFrom;
  @BindView(R.id.txt_due_date)
  TextView mTxtDueDate;
  @BindView(R.id.ll_date)
  LinearLayout mLlDate;
  @BindView(R.id.checkbox_repeat_task)
  CheckBox mCheckboxRepeatTask;
  @BindView(R.id.btn_submit)
  Button mBtnSubmit;
  @BindView(R.id.progressbar)
  ProgressBar mProgressbar;
  private AddTaskPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_task);
    ButterKnife.bind(this);

    //Set up presenter
    presenter = new AddTaskPresenter();
    presenter.attachView(this);
    presenter.EnableRuntimePermission();

    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle(R.string.add_new_task);
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

  @OnClick({R.id.txt_who, R.id.txt_remaind_from, R.id.txt_due_date, R.id.checkbox_repeat_task,
      R.id.btn_submit})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.txt_who:
        presenter.pickContact();
        break;
      case R.id.txt_remaind_from:
        openCalender(true);
        break;
      case R.id.txt_due_date:
        openCalender(false);
        break;
      case R.id.checkbox_repeat_task:
        if (mCheckboxRepeatTask.isChecked()) {
          presenter.showDialog(this);
        } else {

        }
        break;
      case R.id.btn_submit:

        // Hide Keyboard
        Functions.hideSoftKeyboard(AddTaskActivity.this);
        String mobileNumber = mTxtWho.getText().toString().trim();
        String firstName = mEdtFirstName.getText().toString().trim();
        String reminderDescription = mEdtWhat.getText().toString().trim();
        String reminder_date_from = mTxtRemaindFrom.getText().toString().trim();
        String reminder_date_to = mTxtDueDate.getText().toString().trim();

        String reminder_is_repeat = "1";
        //String reminder_when_to_repeat = "Daily";
        //String reminder_repeat_every = "2";

        presenter.addTask(firstName, mobileNumber, reminderDescription,
            reminder_date_from, reminder_date_to, reminder_is_repeat);
        break;
    }
  }

  private void openCalender(final boolean setToFromTextview) {

    // Get Current Date
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
        new DatePickerDialog.OnDateSetListener() {

          @Override
          public void onDateSet(DatePicker view, int year,
              int monthOfYear, int dayOfMonth) {
            if (setToFromTextview) {
              mTxtRemaindFrom.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            } else {
              mTxtDueDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }


          }
        }, mYear, mMonth, mDay);
    datePickerDialog.show();
  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public void showProgressIndicator(boolean show) {
    if (show) {
      mProgressbar.setVisibility(View.VISIBLE);
    } else {
      mProgressbar.setVisibility(View.GONE);
    }
  }

  @Override
  public void addTask() {

  }


  @Override
  public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

    switch (RC) {

      case AddTaskPresenter.RequestPermissionCode:

        if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

          // Toast.makeText(this, "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

        } else {

          Toast.makeText(this, "Permission Canceled, Now your application cannot access CONTACTS.",
              Toast.LENGTH_LONG).show();

        }
        break;
    }
  }

  @Override
  public void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {

    super.onActivityResult(RequestCode, ResultCode, ResultIntent);

    switch (RequestCode) {

      case (7):
        if (ResultCode == Activity.RESULT_OK) {

          Uri uri;
          Cursor cursor1, cursor2;
          String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "";
          int IDresultHolder;

          uri = ResultIntent.getData();

          cursor1 = getContentResolver().query(uri, null, null, null, null);

          if (cursor1.moveToFirst()) {

            TempNameHolder = cursor1
                .getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            TempContactID = cursor1
                .getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

            IDresult = cursor1
                .getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            IDresultHolder = Integer.valueOf(IDresult);

            if (IDresultHolder == 1) {

              cursor2 = getContentResolver()
                  .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                      ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID,
                      null, null);

              while (cursor2.moveToNext()) {

                TempNumberHolder = cursor2.getString(
                    cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                mEdtFirstName.setText(TempNameHolder);
                mEdtFirstName.setSelection(mEdtFirstName.length());

                mTxtWho.setText(TempNumberHolder);

              }
            }

          }
        }
        break;
    }
  }

  @Override
  protected void onDestroy() {
    presenter.detachView();
    super.onDestroy();
  }

}
