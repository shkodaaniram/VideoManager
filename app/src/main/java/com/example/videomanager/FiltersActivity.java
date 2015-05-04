package com.example.videomanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View.OnClickListener;


public class FiltersActivity extends ActionBarActivity implements OnClickListener {

    ProgressDialog progressDialog;
    VideoView videoView;
    Button downloadVBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        downloadVBtn = (Button)findViewById(R.id.downloadVBtn);
        downloadVBtn.setOnClickListener(this);
        videoView = (VideoView)findViewById(R.id.videoView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloadVBtn:
                progressDialog = new ProgressDialog(FiltersActivity.this);
                // Set progressbar title
                progressDialog.setTitle("Downloading video...");
                // Set progressbar message
                progressDialog.setMessage("Buffering...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                // Show progressbar
                progressDialog.show();

                try {
                    // Start the MediaController
                    MediaController mediaController = new MediaController(
                            FiltersActivity.this);
                    mediaController.setAnchorView(videoView);
                    //Uri video = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sdcard/Videos/Despicable-Me-2");
                    Uri video = Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);


                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                videoView.requestFocus();
                videoView.setOnPreparedListener(new OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        progressDialog.dismiss();
                        videoView.start();
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
