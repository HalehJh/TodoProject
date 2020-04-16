package com.hj.todoproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks = new ArrayList<>();
    private TaskEventListener eventListener;

    public TaskAdapter(TaskEventListener eventListener){
        this.eventListener = eventListener;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
      holder.bind(tasks.get(position));
    }

    public void addTask(Task task){
        tasks.add(0,task);
        notifyItemInserted(0);
    }


    public void addTasks(List<Task> items){
        tasks.clear();
        tasks.addAll(items);
        notifyDataSetChanged();
    }

    public void deleteItem(Task task){
        for (int i = 0; i <tasks.size() ; i++) {
            if (tasks.get(i).getId()==task.getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void deleteItems(){
        tasks.clear();
        //if we want to change all data in adapter we use notifyDataSetChanged
        notifyDataSetChanged();
    }

    public void editItem(Task task){
        for (int i = 0; i <tasks.size() ; i++) {
            if (tasks.get(i).getId()==task.getId()){
                tasks.set(i,task);
                notifyItemChanged(i);
            }
        }
    }

    public void getSearchTask(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        ImageView btn_task_delete;
        CheckBox checkBox_task;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox_task = itemView.findViewById(R.id.checkBox_task);
            btn_task_delete = itemView.findViewById(R.id.btn_task_delete);
        }

        private void bind(final Task task){
            checkBox_task.setOnCheckedChangeListener(null);
            checkBox_task.setChecked(task.isCompleted());
            checkBox_task.setText(task.getTitle());

            btn_task_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onDeleteListener(task);
                }
            });

          itemView.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View v) {
                  eventListener.onEditListener(task);
                  return false;
              }
          });

          checkBox_task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  task.setCompleted(isChecked);
                  eventListener.onCheckedChangeListener(task);
              }
          });
        }
    }

    public interface TaskEventListener{
        void onDeleteListener(Task task);
        void onEditListener(Task task);
        void onCheckedChangeListener(Task task);
    }
}
