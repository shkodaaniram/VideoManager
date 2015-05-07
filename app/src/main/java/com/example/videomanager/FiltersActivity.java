package com.example.videomanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View.OnClickListener;

import java.io.IOException;


public class FiltersActivity extends ActionBarActivity implements OnClickListener, SurfaceHolder.Callback {

    ProgressDialog progressDialog;
    VideoView videoView;
    Button downloadVBtn;
    Button openCameraBtn;
    ImageButton filter_noneBtn;
    ImageButton filter_sepiaBtn;
    ImageButton filter_aquaBtn;

    Camera.Parameters params;

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

        filter_noneBtn = (ImageButton)findViewById(R.id.filter_none);
        filter_noneBtn.setOnClickListener(this);
        filter_sepiaBtn = (ImageButton)findViewById(R.id.filter_sepia);
        filter_sepiaBtn.setOnClickListener(this);
        filter_aquaBtn = (ImageButton)findViewById(R.id.filter_aqua);
        filter_aquaBtn.setOnClickListener(this);

        videoView = (VideoView)findViewById(R.id.videoView);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

       // mTextureView = new TextureView(this);

        surfaceView = (SurfaceView)findViewById(R.id.cameraPreview);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        /*controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.control_filters, null);
        ViewGroup.LayoutParams layoutParamsControl
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addContentView(viewControl, layoutParamsControl);*/


    }

   /* @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        if(camera == null)
            camera = Camera.open();
        params = camera.getParameters();
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
    }*/

    void stopVideo()
    {
        if(videoView != null)
        {
            videoView.stopPlayback();
            //videoView.setBackgroundResource(0);
            videoView.setVisibility(View.INVISIBLE);
        }

        surfaceView.setVisibility(surfaceView.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloadVBtn:
                isClicked = false;
                surfaceView.setVisibility(surfaceView.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                if(previewing){
                    camera.stopPreview();
                    camera.release();
                    camera = null;
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
                    //Uri video = Uri.parse(Environment.getExternalStorageDirectory()+"/Videos/Despicable-Me-2");
                    Uri video = Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
                    //Environment.getExternalStorageDirectory().getAbsolutePath()
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                   // videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/Videos/Despicable-Me-2");


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
                /*controlInflater = LayoutInflater.from(getBaseContext());
                View viewControl = controlInflater.inflate(R.layout.control_filters, null);
                ViewGroup.LayoutParams layoutParamsControl
                        = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                this.addContentView(viewControl, layoutParamsControl);*/

                break;
            case R.id.openCameraBtn:
                //mTextureView.setSurfaceTextureListener(this);
                //setContentView(mTextureView);

                if(videoView != null)
                {
                    videoView.stopPlayback();
                    //videoView.setBackgroundResource(0);
                    videoView.setVisibility(View.INVISIBLE);
                }

                surfaceView.setVisibility(surfaceView.VISIBLE);
                isClicked = true;
                if(camera == null)
                    camera = Camera.open();
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                    previewing = true;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
            case R.id.filter_none:
                //stopVideo();
                if(isClicked){
                    if(camera == null)
                        camera = Camera.open();
                    params = camera.getParameters();
                    params.setColorEffect(Camera.Parameters.EFFECT_NONE);
                    camera.setParameters(params);
                }
                break;
            case R.id.filter_sepia:
                //stopVideo();
                if(isClicked) {
                    if (camera == null)
                        camera = Camera.open();
                    params = camera.getParameters();
                    params.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
                    camera.setParameters(params);
                }
                break;
            case R.id.filter_aqua:
               // stopVideo();
                if(isClicked) {
                    if (camera == null)
                        camera = Camera.open();
                    params = camera.getParameters();
                    params.setColorEffect(Camera.Parameters.EFFECT_AQUA);
                    camera.setParameters(params);
                }
                break;
            default:
                break;
        }
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);

        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
// TODO Auto-generated method stub
        /*Camera.Parameters params = camera.getParameters();
        params.setColorEffect(Camera.Parameters.EFFECT_NEGATIVE);
        camera.setParameters(params);*/
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }
        setCameraDisplayOrientation(this, 0, camera);
        /*if (camera != null){
            try {

                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();
                        previewing = true;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
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
