/**
License: GPL 2.0

Copyright (C) 2012 Benson Trinh, Thomas Polasek, Remco Uittenbogerd, Clinton Wong

This program is free software; you can redistribute it and/or modify it under the 
terms of the GNU General Public License as published by the Free Software Foundation;

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; 
if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
MA 02110-1301, USA.
 **/
package com.cmput301.classproject.UI;

import java.util.*;

import com.cmput301.classproject.R;
import com.cmput301.classproject.Model.Submission;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SubmissionViewActivity extends Activity implements Observer {

	private Submission submission = null; // this is passed to from
											// TaskViewActivity

	private ArrayList<Bitmap> photosTaken = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submission_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);


		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.submission = (Submission) extras.getSerializable("Submission");
		}

		if (submission == null) {
			finish();
		} else {
			photosTaken = submission.getImages();
			
			// Start recognizing the Layout elements, and fill out activity views using the submission object
			GridView gridView = (GridView) findViewById(R.id.submissionPhotoList);
			gridView.setAdapter(new ImageAdapter(this));
			
			TextView authorText = (TextView) findViewById(R.id.view_author_name);
			authorText.setText(submission.getAuthor());
			
			TextView submissionSummary = (TextView) findViewById(R.id.view_submission_summary);
			submissionSummary.setText(submission.getSummary());
			
			TextView submissionText = (TextView) findViewById(R.id.view_submission_text);
			submissionText.setText(submission.getText());
			
			TextView access = (TextView) findViewById(R.id.view_submission_access);			
			access.setText(submission.getAccess().toString());
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
	 * Name: okayClicked Description: User is finished viewing the submission
	 * this will redirect them back to the ViewTasksActivity
	 * 
	 * @param v
	 */
	public void okayClicked(View v) {
		finish();
	}

	/**
	 * Name: update Description: Model has called to update the view with new
	 * data. This will update the Submission Activity with the new data.
	 * 
	 * @param s
	 * @param arg
	 */
	public void update(Observable s, Object arg) {
		// TODO Auto-generated method stub

	}/**
	 * ImageAdapter Class to support putting thumbnails into list views.
	 * Will also have a modified implementation into ViewSubmission activity.
	 * This is tailored to fit ListViews.
	 */
	public class ImageAdapter extends BaseAdapter
	{
		private Context context;
		
		public ImageAdapter(Context C)
		{
			context = C;
		}
		
		public int getCount() {
			return photosTaken.size();
		}

		public Object getItem(int arg0) {
			return photosTaken.get(arg0);
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            }
            else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(photosTaken.get(position));
            return imageView;
		}
		
	}
}
