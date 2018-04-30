package com.example.abhipubali.cameraappintent;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import com.example.abhipubali.cameraappintent.utility.JasonParser;
import com.example.abhipubali.cameraappintent.utility.NetworkCaller;
import com.example.abhipubali.cameraappintent.utility.VoiceOutput;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    public static JasonParser jasonParser = new JasonParser();
    ImageView mImageView;
    TextView tv;
    public final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET};
    private static final int PERMISSION_REQUEST_CODE = 200;
    //public static VoiceOutput voiceOutput;
    public static TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mImageView = (ImageView)this.findViewById(R.id.image_from_camera);

        //**--- textto speech-----**//
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        //------ text to speech------//
    }

    /**
     * Asking and setting permission
     */
    private void setPermissions()
    {
        int len = permissions.length;
        int status[] = new int[len];
        for(int i=0;i<len;i++)
        {
            status[i] = ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]);
            if(status[i]!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermission(new String[]{permissions[i]});
                break;
            }
        }
    }

    /**
     * permission request
     * @param request
     */
    private void requestPermission(String[] request)
    {
        ActivityCompat.requestPermissions(MainActivity.this, request, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            int len = permissions.length;
            for(int i=0; i<len; i++ )
            {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                {
                    //start audio recording or whatever you planned to do
                } else if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]))

                    {
                        //Show an explanation to the user *asynchronously*
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        final String thispermission = permissions[i];
                        builder.setMessage("This permission is important to take picture")
                                .setTitle("Important permission required");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{thispermission}, PERMISSION_REQUEST_CODE);
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
    /**
     *
     * @param view
     */
    public void takeImageFromCamera(View view)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
       // File imageFile = new File(imageFilePath);
       // Uri imageFileUri = Uri.fromFile(imageFile);

        //cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
        jasonParser.reset();
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
        File imageFile = new File(imageFilePath);
        Uri uri = Uri.fromFile(imageFile);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mPhoto = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(mPhoto);
            tv =  (TextView)this.findViewById(R.id.results_json);
            new NetworkCaller(mPhoto,tv).execute();
            /*Bitmap mPhoto = null;
            try {
                mPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                mImageView.setImageBitmap(mPhoto);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }
    }

}
