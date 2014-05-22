package com.appacitive.gentlemanstodo.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appacitive.core.AppacitiveConnection;
import com.appacitive.core.AppacitiveObject;
import com.appacitive.core.model.Callback;
import com.appacitive.core.model.ConnectedObject;
import com.appacitive.core.model.ConnectedObjectsResponse;

import java.util.ArrayList;
import java.util.List;


public class TasksActivity extends ActionBarActivity {

    Context mContext;
    ListView mListView;
    long mUserId;
    TasksAdapter mAdapter;
    TextView mNewTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mListView = (ListView) findViewById(R.id.listView);
        mUserId = getIntent().getLongExtra("user_id", 0);
        String firstname = getIntent().getStringExtra("firstname");
        Toast.makeText(this, "Welcome Monsieur " + firstname, Toast.LENGTH_LONG).show();
        mContext = this;


        setTitle("Your tasks, Sir");

        //  Add code to fetch tasks connected to your logged in user here.
        AppacitiveObject.getConnectedObjectsInBackground("owner", "user", mUserId, null, null, new Callback<ConnectedObjectsResponse>() {
            @Override
            public void success(ConnectedObjectsResponse result) {
                List<AppacitiveObject> tasks = new ArrayList<AppacitiveObject>();
                for (ConnectedObject connectedObject : result.results) {
                    tasks.add(connectedObject.object);
                }
                mAdapter = new TasksAdapter(mContext, R.layout.item_tasks, tasks);
                setupHeader();
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void failure(ConnectedObjectsResponse result, Exception e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupHeader()
    {
        View headerView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tasks_header, null, false);
        mNewTaskName = (TextView) headerView.findViewById(R.id.new_task);
        Button addNewTaskButton = (Button) headerView.findViewById(R.id.btn_add_task);
        addNewTaskButton.setOnClickListener(new addNewTaskButtonClickListener());
        mListView.addHeaderView(headerView);
    }

    public class addNewTaskButtonClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            String newTaskName = mNewTaskName.getText().toString();

            if (newTaskName.trim().length() == 0)
                return;

            mNewTaskName.setText("");

            //  Adding a new task and connecting it to the logged in user.
            AppacitiveObject task = new AppacitiveObject("todo");
            task.setStringProperty("title", newTaskName.trim());
            task.setBoolProperty("completed", false);

            new AppacitiveConnection("owner")
                    .toExistingObject("user", mUserId)
                    .fromNewObject("todo", task)
                    .createInBackground(new Callback<AppacitiveConnection>() {
                        @Override
                        public void success(AppacitiveConnection result) {
                            Toast.makeText(mContext, "New task added", Toast.LENGTH_LONG).show();
                            mAdapter.add((AppacitiveObject) result.endpointA.object);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void failure(AppacitiveConnection result, Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
