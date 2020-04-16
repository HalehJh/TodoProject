package com.hj.todoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskEventListener, taskDialog.addNewTaskCallback, EditTaskDialog.editTaskEventListener {

    TaskAdapter taskAdapter = new TaskAdapter(this);
    TaskDao taskDao;
    RecyclerView rv_main_tasks;
    List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao = AppDatabase.getAppDatabase(this).getTaskDao();

        EditText et_main = findViewById(R.id.et_main);

        et_main.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    List<Task> tasks = taskDao.search(s.toString());
                    taskAdapter.getSearchTask(tasks);
                }else {
                    List<Task> tasks = taskDao.getAll();
                    taskAdapter.addTasks(tasks);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tasks = taskDao.getAll();
        taskAdapter.addTasks(tasks);
        rv_main_tasks = findViewById(R.id.rv_main_tasks);
        rv_main_tasks.setLayoutManager(new LinearLayoutManager(this));
        rv_main_tasks.setAdapter(taskAdapter);

        View fb_main_addTask = findViewById(R.id.fab_main_addNewTask);
        fb_main_addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDialog taskDialog = new taskDialog();
                taskDialog.show(getSupportFragmentManager(), null);
            }
        });

        View im_clear_all = findViewById(R.id.im_clear_all);
        im_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDao.deleteAll();
                taskAdapter.deleteItems();
            }
        });
    }

    @Override
    public void onDeleteListener(Task task) {
        int result = taskDao.delete(task);
        if (result>0){
            taskAdapter.deleteItem(task);
        }
    }

    @Override
    public void onEditListener(Task task) {
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        editTaskDialog.setArguments(bundle);
        editTaskDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onCheckedChangeListener(Task task) {
        taskDao.update(task);
    }

    @Override
    public void onEditDialog(Task task) {
        int result = taskDao.update(task);
        if (result>0){
            taskAdapter.editItem(task);
        }
    }

    @Override
    public void onAddNewTask(Task task) {
        long result = taskDao.add(task);
        if (result!=-1){
            task.setId(result);
            taskAdapter.addTask(task);
            rv_main_tasks.smoothScrollToPosition(0);
        }
    }
}
