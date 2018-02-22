package com.example.abhipubali.cameraapp;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.abhipubali.cameraapp.utility.CameraPreview;

public class MainActivity extends AppCompatActivity {
    private Camera camera = null;
    private int cameraId = 0;
    private CameraPreview mPreview;
    public final String[] permissions = {Manifest.permission.CAMERA};
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(this, "No Camera Found1", Toast.LENGTH_LONG).show();
        }
        else{
            cameraId = findCamera();
            if(cameraId < 0){
                Toast.makeText(this,"No camera found2",Toast.LENGTH_LONG).show();
            }
            else {
                System.out.println("***camera open  "+cameraId);
                setPermissions();
                //if(camera!=null)
                try {
                    camera = Camera.open(cameraId);
                }catch(Exception e){
                    e.printStackTrace();
                }
                mPreview = new CameraPreview(this, camera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview,0);

            }
        }
    }
    private void setPermissions()
    {
        int len = permissions.length;
        int status[] = new int[len];
        for(int i=0;i<len;i++)
        {
            status[i] = ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]);
            if(status[i]!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermission(permissions);
                break;
            }
        }
    }

    private void requestPermission(String[] request)
    {
        ActivityCompat.requestPermissions(MainActivity.this, request, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            int len = permissions.length;
            //for(int i=0; i<len; i++ )
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //start audio recording or whatever you planned to do
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[0]))

                    {
                        //Show an explanation to the user *asynchronously*
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("This permission is important to take picture")
                                .setTitle("Important permission required");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
                            }
                        });
                        //ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST_CODE);
                    } else {
                        //Never ask again and handle your app without permission.
                    }
                }
            }
        }
    }
    public void onClick(View view){
        Log.d("clicked", "Clicked");
        //camera.startPreview();


    }
    @Override
    protected void onPause(){
        if(camera != null){
            camera.release();
            camera = null;
        }
        super.onPause();
    }
    private int findCamera(){
        int camId =-1;
        int numOfCam = Camera.getNumberOfCameras();
        Log.e("NumOfCamera", ""+numOfCam);
        for(int i=0;i<numOfCam;i++)
        {
            Camera.CameraInfo camInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, camInfo);
            if(camInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)//CAMERA_FACING_BACK
            {
                camId = i;
                //System.out.print("NUmof camera===="+numOfCam+"  "+i);
                break;
            }
        }
        return camId;
    }
}
