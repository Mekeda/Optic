package com.example.abhipubali.cameraappintent.utility;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhipubali.cameraappintent.R;

import static android.content.ContentValues.TAG;

/**
 * Created by abhipubali on 2/26/18.
 */

public class NetworkCaller extends AsyncTask<String, Void, String> {
    Bitmap bitImg;
    TextView tv;
    public NetworkCaller(Bitmap bitImg, TextView tv)
    {

        this.bitImg = bitImg;
        this.tv = tv;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        String result = null;
        try {
            result =  NetworkUtilities.AzureRequest(bitImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result)
    {

        if (result != null && !result.equals("")) {
            tv.setText(result);
            Log.d(TAG, result);

        } else {
            tv.setText("error");
            Log.e(TAG, "error");
        }
    }
}
