package com.hj.todoproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditTaskDialog extends DialogFragment {

    private editTaskEventListener eventListener;
    Task task = new Task();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventListener = (editTaskEventListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task, null, false);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        task = getArguments().getParcelable("task");
        if (task==null){
            dismiss();
        }
        View btn_dialogEdit_save = view.findViewById(R.id.btn_dialogEdit_save);
        final TextInputEditText et_dialogEdit_title = view.findViewById(R.id.et_dialogEdit_title);
        final TextInputLayout etl_dialogEdit_title = view.findViewById(R.id.etl_dialogEdit_title);

        et_dialogEdit_title.setText(task.getTitle());

        btn_dialogEdit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_dialogEdit_title.length()>0){
                    task.setTitle(et_dialogEdit_title.getText().toString());
                    eventListener.onEditDialog(task);
                    dismiss();

                }else {
                    etl_dialogEdit_title.setError("عنوان نباید خالی باشد");
                }
            }
        });

        return alert.create();
    }

    public interface editTaskEventListener{
        void onEditDialog(Task task);
    }
}
