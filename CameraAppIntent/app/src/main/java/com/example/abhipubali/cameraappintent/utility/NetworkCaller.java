package com.example.abhipubali.cameraappintent.utility;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhipubali.cameraappintent.MainActivity;
import com.example.abhipubali.cameraappintent.R;

import org.json.JSONException;

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
            result =  NetworkUtilities.AzureRequest2(bitImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result)
    {

        if (result != null && !result.equals("")) {
            try {
                MainActivity.jasonParser.jsonParsing(result);
                StringBuffer str = new StringBuffer();
                str.append("Captions:==");
                for (String s: MainActivity.jasonParser.getCaptions())
                    str.append(s+"\n");

                str.append("Tags:==");
                for (String t: MainActivity.jasonParser.getListOfItem())
                    str.append(t+"\n");

                str.append("categories:==");
                for (String t: MainActivity.jasonParser.getListOfCategories())
                    str.append(t+"\n");

                tv.setText(str.toString());
                Log.d(TAG, str.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            tv.setText("error");
            Log.e(TAG, "error");
        }
    }
}
