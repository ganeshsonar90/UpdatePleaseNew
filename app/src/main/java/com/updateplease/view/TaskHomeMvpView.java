package com.updateplease.view;

import com.updateplease.model.ReminderTask;
import java.util.List;

/**
 * Created by gboss on 04/11/18.
 */

public interface TaskHomeMvpView extends MvpView {

  void showProgressIndicator(boolean show);

  void showTasks(List<ReminderTask> tasks);
}
