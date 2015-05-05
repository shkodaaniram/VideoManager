package com.example.videomanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View.OnClickListener;

import java.io.IOException;


public class FiltersActivity extends ActionBarActivity implements OnClickListener, TextureView.SurfaceTextureListener {

    ProgressDialog progressDialog;
    VideoView videoView;
    Button downloadVBtn;
    Button openCameraBtn;

    Camera camera;
    private TextureView mTextureView;
    boolean isClicked = false;

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        downloadVBtn = (Button)findViewById(R.id.downloadVBtn);
        downloadVBtn.setOnClickListener(this);
        openCameraBtn = (Button)findViewById(R.id.openCameraBtn);
        openCameraBtn.setOnClickListener(this);
        videoView = (VideoView)findViewById(R.id.videoView);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mTextureView = new TextureView(this);

        /*surfaceView = (SurfaceView)findViewById(R.id.cameraPreview);

        getWindow().setFormat(PixelFormat.UNKNOWN);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        controlInflater = LayoutInflater.from(getBaseContext());*/

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        params.setColorEffect(Camera.Parameters.EFFECT_NEGATIVE);
        params.setPreviewSize(videoView.getWidth(), videoView.getHeight());
        camera.setParameters(params);
        camera.startPreview();
        if (camera == null) {
            // Seeing this on Nexus 7 2012 -- I guess it wants a rear-facing camera, but
            // there isn't one.  TODO: fix
            throw new RuntimeException("Default camera not available");
        }

        try {
            camera.setPreviewTexture(surface);
            camera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        camera.stopPreview();
        camera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
        //Log.d(TAG, "updated, ts=" + surface.getTimestamp());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloadVBtn:
                /*if(camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                    previewing = false;
                }*/
                if(previewing){
                    camera.stopPreview();
                    previewing = false;
                }
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
            case R.id.openCameraBtn:
                mTextureView.setSurfaceTextureListener(this);
                setContentView(mTextureView);
                break;
            default:
                break;
        }
    }

   /* @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
// TODO Auto-generated method stub
        Camera.Parameters params = camera.getParameters();
        params.setColorEffect(Camera.Parameters.EFFECT_NEGATIVE);
        camera.setParameters(params);
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
// TODO Auto-generated method stub
        camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }*/


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
