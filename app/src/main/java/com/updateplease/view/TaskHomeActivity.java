package com.updateplease.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.updateplease.R;
import com.updateplease.adpter.TaskListAdapter;
import com.updateplease.helper.AppConstant;
import com.updateplease.helper.Functions;
import com.updateplease.model.ReminderTask;
import com.updateplease.presenter.TaskHomePresenter;
import java.util.List;

public class TaskHomeActivity extends AppCompatActivity implements TaskHomeMvpView {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.txt_all)
  TextView mTxtAll;
  @BindView(R.id.txt_from_date)
  TextView mTxtFromDate;
  @BindView(R.id.txt_to_date)
  TextView mTxtToDate;
  @BindView(R.id.ll_header)
  LinearLayout mLlHeader;
  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @BindView(R.id.progressbar)
  ProgressBar mProgressbar;
  @BindView(R.id.rl_add_task)
  RelativeLayout mRlAddTask;
  private TaskHomePresenter presenter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_home);
    ButterKnife.bind(this);

    //Set up presenter
    presenter = new TaskHomePresenter();
    presenter.attachView(this);

    setSupportActionBar(mToolbar);
    //Set up RecyclerView
    setupRecyclerView(mRecyclerView);


  }

  @Override
  protected void onResume() {
    super.onResume();
    if (presenter != null) {
      presenter
          .getRemainderTasks(AppConstant.TASK_STATUS_PENDING, 1, 10, Functions.getCurrentDate(),
              "2019-11-30");
    }
  }

  @OnClick({R.id.txt_all, R.id.txt_from_date, R.id.txt_to_date, R.id.rl_add_task})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.txt_all:
        break;
      case R.id.txt_from_date:
        break;
      case R.id.txt_to_date:
        break;
      case R.id.rl_add_task:
        presenter.startAddTaskActivity();
        break;
    }
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    TaskListAdapter adapter = new TaskListAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
  public void showTasks(List<ReminderTask> tasks) {
    TaskListAdapter adapter = (TaskListAdapter) mRecyclerView.getAdapter();
    adapter.setTasks(tasks);
    adapter.notifyDataSetChanged();
    mRecyclerView.setVisibility(View.VISIBLE);
  }


  @Override
  protected void onDestroy() {
    presenter.detachView();
    super.onDestroy();
  }

  @Override
  public Context getContext() {
    return this;
  }

}
