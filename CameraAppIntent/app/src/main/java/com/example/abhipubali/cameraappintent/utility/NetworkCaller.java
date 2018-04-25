package com.example.abhipubali.cameraappintent.utility;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
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
           // result = "speak out loud. This is a testing message";
            //MainActivity.t1.speak(result, TextToSpeech.QUEUE_FLUSH, null);

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


                if(MainActivity.jasonParser.getCaptions()!=null) {
                    str.append(Dialog.captionDialog);
                    str.append(MainActivity.jasonParser.getCaptionToString());

                }
                else
                    str.append(Dialog.capEmpty);
                str.append("\n");
                if(MainActivity.jasonParser.getListOfItem()!=null) {
                    str.append(Dialog.tagDialog);
                    str.append(MainActivity.jasonParser.getListOfItemToString());
                }
                else{
                    str.append(Dialog.tagListEmpty);
                }
                str.append("\n");
                if(MainActivity.jasonParser.getListOfCategories()!=null) {
                   str.append(Dialog.category);
                   str.append(MainActivity.jasonParser.getListOfCategoriesToString());
                }
                else {
                    str.append(Dialog.categoryEmpty);
                }
                tv.setText(str.toString());
                MainActivity.t1.speak(str.toString(), TextToSpeech.QUEUE_FLUSH, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            tv.setText("error");
            Log.e(TAG, "error");
        }
    }
}
