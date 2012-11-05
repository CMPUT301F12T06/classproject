package com.cmput301.classproject.UI;

import com.cmput301.classproject.R;
import com.cmput301.classproject.R.layout;
import com.cmput301.classproject.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Hello?!");
        setContentView(R.layout.activity_image_view);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        ImageView image = (ImageView) findViewById(R.id.selectedPhoto);
        image.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_view, menu);
        return true;
    }
}
