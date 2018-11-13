package com.updateplease.adpter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.updateplease.R;
import com.updateplease.helper.AppConstant;
import com.updateplease.model.ReminderTask;
import java.util.Collections;
import java.util.List;

/**
 * Created by gboss on 04/11/18.
 */


public class TaskListAdapter extends Adapter<TaskListAdapter.TaskViewHolder> {

  private List<ReminderTask> tasks;
  private Callback callback;

  public TaskListAdapter() {
    this.tasks = Collections.emptyList();
  }

  public TaskListAdapter(List<ReminderTask> tasks) {
    this.tasks = tasks;
  }

  public void setTasks(List<ReminderTask> tasks) {
    this.tasks = tasks;
  }

  public void setCallback(Callback callback) {
    this.callback = callback;
  }

  @Override
  public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_task, parent, false);
    final TaskViewHolder viewHolder = new TaskViewHolder(itemView);
    viewHolder.mBtnAction.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (callback != null) {
          callback.onItemClick(viewHolder.position);
        }
      }
    });
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(TaskViewHolder holder, int position) {
    ReminderTask task = tasks.get(position);
    holder.position = position;

    if (position != 0) {
      holder.mLlHeder.setVisibility(View.GONE);
    } else {
      holder.mLlHeder.setVisibility(View.VISIBLE);
    }

    if (task != null) {

      if (!TextUtils.isEmpty(task.getFname())) {
        holder.mTxtWho.setText(task.getFname());
      }

      if (!TextUtils.isEmpty(task.getReminderDesc())) {
        holder.mTxtWhat.setText(task.getReminderDesc());
      }

      if (!TextUtils.isEmpty(task.getReminderDateTo())) {
        holder.mTxtDate.setText(task.getReminderDateTo());
      }

      int taskStatus = task.getReminderStatus();
      if (taskStatus == 0) {
        holder.mTxtStatus.setText(AppConstant.TASK_STATUS_PENDING);
        holder.mBtnAction.setText("Done");
      } else if (taskStatus == 1) {
        holder.mTxtStatus.setText(AppConstant.TASK_STATUS_COMPLETED);
        holder.mBtnAction.setText("Reassign");
      } else {
        holder.mTxtStatus.setText(AppConstant.TASK_STATUS_CANCELLED);
      }

      if (!TextUtils.isEmpty(task.getFname())) {
        // holder.mTxtDayLeft.setText("1 day over");
      }

    }

  }

  @Override
  public int getItemCount() {
    //return 10;
    return tasks.size();
  }

  public interface Callback {

    void onItemClick(int task);
  }

  static class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ll_heder)
    LinearLayout mLlHeder;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.txt_who)
    TextView mTxtWho;
    @BindView(R.id.txt_what)
    TextView mTxtWhat;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_day_left)
    TextView mTxtDayLeft;
    @BindView(R.id.btn_action)
    Button mBtnAction;
    public int position;

    TaskViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}

