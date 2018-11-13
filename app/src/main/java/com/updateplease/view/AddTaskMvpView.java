package com.updateplease.view;

import com.updateplease.model.ReminderTask;
import java.util.List;

/**
 * Created by gboss on 10/11/18.
 */

public interface AddTaskMvpView extends MvpView {

  void showProgressIndicator(boolean show);

  void addTask();
}
