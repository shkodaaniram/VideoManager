package com.example.videomanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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

    private int PICK_REQUEST = 1;

    ProgressDialog progressDialog;
    VideoView videoView;
    Button downloadVBtn;
    Button openCameraBtn;
    ImageButton filter_noneBtn;
    ImageButton filter_sepiaBtn;
    ImageButton filter_aquaBtn;
    ImageButton filter_negativeBtn;
    ImageButton filter_blackboardBtn;
    ImageButton filter_monoBtn;
    ImageButton filter_posterizeBtn;
    ImageButton filter_solarizeBtn;
    ImageButton filter_whiteboardBtn;

    Camera.Parameters params;

    Camera camera;
    private TextureView mTextureView;
    boolean isClicked = false;

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;

    private RecyclerView recycleView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       /* downloadVBtn = (Button) findViewById(R.id.downloadVBtn);
        downloadVBtn.setOnClickListener(this);
        openCameraBtn = (Button) findViewById(R.id.openCameraBtn);
        openCameraBtn.setOnClickListener(this);*/

        filter_noneBtn = (ImageButton) findViewById(R.id.filter_none);
        filter_noneBtn.setOnClickListener(this);
        filter_sepiaBtn = (ImageButton) findViewById(R.id.filter_sepia);
        filter_sepiaBtn.setOnClickListener(this);
        filter_aquaBtn = (ImageButton) findViewById(R.id.filter_aqua);
        filter_aquaBtn.setOnClickListener(this);
        filter_negativeBtn = (ImageButton) findViewById(R.id.filter_negative);
        filter_negativeBtn.setOnClickListener(this);
        filter_blackboardBtn = (ImageButton) findViewById(R.id.filter_blackboard);
        filter_blackboardBtn.setOnClickListener(this);
        filter_monoBtn = (ImageButton) findViewById(R.id.filter_mono);
        filter_monoBtn.setOnClickListener(this);
        filter_posterizeBtn = (ImageButton) findViewById(R.id.filter_posterize);
        filter_posterizeBtn.setOnClickListener(this);
        filter_solarizeBtn = (ImageButton) findViewById(R.id.filter_solarize);
        filter_solarizeBtn.setOnClickListener(this);
        filter_whiteboardBtn = (ImageButton) findViewById(R.id.filter_whiteboard);
        filter_whiteboardBtn.setOnClickListener(this);

        videoView = (VideoView) findViewById(R.id.videoView);

        surfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        /*int[] mImages = {R.drawable.ic_action_video, R.drawable.ic_action_video, R.drawable.ic_action_video,
                R.drawable.ic_action_video, R.drawable.ic_action_video, R.drawable.ic_action_video,
                R.drawable.ic_action_video, R.drawable.ic_action_video, R.drawable.ic_action_video};
        recycleView = (RecyclerView) findViewById(R.id.recycler_view);
        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(mImages);
        recycleView.setAdapter(adapter);*/
    }

    private void setTypeOfFilter(String filter){
        if (isClicked) {
            if (camera == null) {
                camera = Camera.open();
            }
            params = camera.getParameters();
            params.setColorEffect(filter);
            camera.setParameters(params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.downloadVBtn:
                isClicked = false;
                surfaceView.setVisibility(surfaceView.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                if (previewing) {
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
                    Uri video = Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/Despicable-Me-2.mp4");
                    //Uri video = Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
                    //Environment.getExternalStorageDirectory().getAbsolutePath()
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                    //videoView.setVideoPath(Environment.getExternalStorageDirectory()+"/sdcard/Videos/Despicable-Me-2");
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
                if (videoView != null) {
                    videoView.stopPlayback();
                    videoView.setVisibility(View.INVISIBLE);
                }
                surfaceView.setVisibility(surfaceView.VISIBLE);
                isClicked = true;
                if (camera == null) {
                    camera = Camera.open();
                }
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                    previewing = true;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;*/
            case R.id.filter_none:
                setTypeOfFilter(Camera.Parameters.EFFECT_NONE);
                break;
            case R.id.filter_sepia:
                setTypeOfFilter(Camera.Parameters.EFFECT_SEPIA);
                break;
            case R.id.filter_aqua:
                setTypeOfFilter(Camera.Parameters.EFFECT_AQUA);
                break;
            case R.id.filter_negative:
                setTypeOfFilter(Camera.Parameters.EFFECT_NEGATIVE);
                break;
            case R.id.filter_posterize:
                setTypeOfFilter(Camera.Parameters.EFFECT_POSTERIZE);
                break;
            case R.id.filter_solarize:
                setTypeOfFilter(Camera.Parameters.EFFECT_SOLARIZE);
                break;
            case R.id.filter_mono:
                setTypeOfFilter(Camera.Parameters.EFFECT_MONO);
                break;
            case R.id.filter_whiteboard:
                setTypeOfFilter(Camera.Parameters.EFFECT_WHITEBOARD);
                break;
            case R.id.filter_blackboard:
                setTypeOfFilter(Camera.Parameters.EFFECT_BLACKBOARD);
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
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
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
        setCameraDisplayOrientation(this, 0, camera);
       /* if (previewing) {
            camera.stopPreview();
            previewing = false;
        }
        setCameraDisplayOrientation(this, 0, camera);
        */
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

    public boolean isNetworkAvaliable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isNetworkAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        return isNetworkAvailable;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filters, menu);

        boolean isNetworkAvailable = isNetworkAvaliable();
        MenuItem shareMenuItem = menu.findItem(R.id.shareItm);
        MenuItem fromAccountItem = menu.findItem(R.id.fromAccountItm);
        shareMenuItem.setVisible(isNetworkAvailable);
        fromAccountItem.setVisible(isNetworkAvailable);
        //FiltersActivity.this.invalidateOptionsMenu();

        return true;
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                final MenuItem shareMenuItem = (MenuItem)findViewById(R.id.shareItm);
                final MenuItem fromAccountItem = (MenuItem)findViewById(R.id.fromAccountItm);
                if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                    NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    boolean connected = info.isConnected();

                    shareMenuItem.setVisible(connected);
                    fromAccountItem.setVisible(connected);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void playingVideo(Uri uri){
        progressDialog = new ProgressDialog(FiltersActivity.this);
        progressDialog.setTitle("Downloading video...");
        progressDialog.setMessage("Buffering...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            MediaController mediaController = new MediaController(
                    FiltersActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            playingVideo(uri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.fromSDcardItm:
                isClicked = false;
                surfaceView.setVisibility(surfaceView.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                if (previewing) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                    previewing = false;
                }
                Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
                mediaChooser.setType("video/*");
                videoView.stopPlayback();
                startActivityForResult(mediaChooser, PICK_REQUEST);
                return true;
            case R.id.fromAccountItm:
                Uri uri = Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
                playingVideo(uri);
                return true;
            case R.id.openCameraItm:
                if (videoView != null) {
                    videoView.stopPlayback();
                    videoView.setVisibility(View.INVISIBLE);
                }
                surfaceView.setVisibility(surfaceView.VISIBLE);
                isClicked = true;
                if (camera == null) {
                    camera = Camera.open();
                }
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                    previewing = true;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
