package com.cmput301.classproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class SubmissionViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_submission_view, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    /**
     * Name: 		okayClicked
     * Description: User is finished viewing the submission
     * 				this will redirect them back to the
     * 				ViewTasksActivity
     * @param v
     */
    public void okayClicked(View v) {
    	Intent intent = new Intent(this, ViewTaskActivity.class);
    	startActivity(intent);
    	
    }
}
