package com.cmput301.classproject.UI;

import java.util.*;

import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.Submission;
import com.cmput301.classproject.R.id;
import com.cmput301.classproject.R.layout;
import com.cmput301.classproject.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SubmissionViewActivity extends Activity implements Observer {

	private Submission submission = null; //this is passed to from TaskViewActivity
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Start recognizing the Layout elements.
        GridView gridView = (GridView) findViewById(R.id.submissionPhotoList);
        
        Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.submission = (Submission) extras.getSerializable("Submission");
		}

		if (submission == null) {
			finish();
		} 
		else{
			TextView temp;

			// TODO make this view look better
			temp = ((TextView) findViewById(R.id.submission_view_summary));
			temp.setText(submission.getSummary());
			
			//Fill out activity view using the submission object
		}

        
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
    	finish();
    }

    /**
     * Name: 		update
     * Description:	Model has called to update the view with new data.
     * 				This will update the Submission Activity with the new data.
     * 
     * @param s
     * @param arg
     */
	public void update(Observable s, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
