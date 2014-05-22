package com.appacitive.gentlemanstodo.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.appacitive.core.AppacitiveObject;
import com.appacitive.core.model.Callback;

import java.util.List;

/**
 * Created by sathley on 5/21/2014.
 */
public class TasksAdapter extends ArrayAdapter<AppacitiveObject> {

    Context mContext;

    public TasksAdapter(Context context, int resource, List<AppacitiveObject> tasks) {
        super(context, resource, tasks);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppacitiveObject task = getItem(position);

        View myView = LayoutInflater.from(getContext()).inflate(R.layout.item_tasks, parent, false);

        CheckBox taskView = (CheckBox) myView.findViewById(R.id.checkbox_task);
        taskView.setTag(position);
        Button deleteButton = (Button) myView.findViewById(R.id.btn_delete_task);
        deleteButton.setTag(position);

        taskView.setText(task.getPropertyAsString("title"));
        Boolean isCompleted = task.getPropertyAsBoolean("completed");
        taskView.setChecked(isCompleted);
        deleteButton.setOnClickListener(null);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                //  Add task delete code here.
                AppacitiveObject task = getItem(position);
                task.deleteInBackground(true, new Callback<Void>() {
                    @Override
                    public void success(Void result) {
                        Toast.makeText(mContext, "Task removed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(Void result, Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                remove(task);
                notifyDataSetChanged();
            }
        });

        taskView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {
                int position = (Integer) compoundButton.getTag();

                //  Add task update code here.
                AppacitiveObject task = getItem(position);
                task.setBoolProperty("completed", isChecked);

                task.updateInBackground(false, new Callback<AppacitiveObject>() {
                    @Override
                    public void success(AppacitiveObject result) {
                        if (isChecked)
                            Toast.makeText(mContext, "Task done", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(mContext, "Task undone", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(AppacitiveObject result, Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return myView;
    }
}
