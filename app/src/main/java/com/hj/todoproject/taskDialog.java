package com.hj.todoproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class taskDialog extends DialogFragment {

    private addNewTaskCallback listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (addNewTaskCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task, null, false);
        View btn_dialog_save = view.findViewById(R.id.btn_dialog_save);
        final TextInputEditText et_dialog_title = view.findViewById(R.id.et_dialog_title);
        final TextInputLayout inputlayout = view.findViewById(R.id.etl_dialog_title);

        btn_dialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_dialog_title.length()>0){
                    Task task = new Task();
                    task.setTitle(et_dialog_title.getText().toString());
                    task.setCompleted(false);
                    listener.onAddNewTask(task);
                    dismiss();
                }else {
                    inputlayout.setError("عنوان نباید خالی باشد");
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public interface addNewTaskCallback{
        void onAddNewTask(Task task);
    }
}
