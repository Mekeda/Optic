package com.example.abhipubali.cameraapp.utility;
// This sample uses the Apache HTTP client library(org.apache.httpcomponents:httpclient:4.2.4)

// and the org.json library (org.json:json:20170516).
//https://docs.microsoft.com/en-us/azure/cognitive-services/computer-vision/quickstarts/java//
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/*import org.apache.http.entity.FileEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
*/
public class NetworkUtilities
{
    // **********************************************
    // *** Update or verify the following values. ***
    // **********************************************

    // Replace the subscriptionKey string value with your valid subscription key.
    public static final String subscriptionKey = "13hc77781f7e4b19b5fcdd72a8df7156";

    // Replace or verify the region.
    //
    // You must use the same region in your REST API call as you used to obtain your subscription keys.
    // For example, if you obtained your subscription keys from the westus region, replace
    // "westcentralus" in the URI below with "westus".
    //
    // NOTE: Free trial subscription keys are generated in the westcentralus region, so if you are using
    // a free trial subscription key, you should not need to change this region.
    public static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze";


    public static void AzureRequest(Bitmap bitmap) throws Exception
    {

        URL url = null;
        Uri builtUri = Uri.parse(uriBase).buildUpon()
                .appendQueryParameter("visualFeatures", "Color,Categories,Tags,Description,ImageType,Faces,Adult")
                .appendQueryParameter("language", "en")
                .build();
        try {
            url = new URL(builtUri.toString());
            HttpURLConnection azrConnection = (HttpURLConnection) url.openConnection();
            azrConnection.setRequestProperty ("Content-Type", "application/json");
            azrConnection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
            azrConnection.setRequestMethod("POST");
            azrConnection.setRequestProperty("Connection", "Keep-Alive");
            azrConnection.setDoOutput(true);

            OutputStream output = azrConnection.getOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);
            output.close();//https://dzone.com/articles/upload-image-to-a-web-service-using-httpurlconnect*/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        /* HttpClient httpclient = new DefaultHttpClient();
        try
        {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
            builder.setParameter("visualFeatures", "Categories,Description,Color");
            builder.setParameter("language", "en");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            //StringEntity reqEntity = new StringEntity("{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg\"}");
            //request.setEntity(reqEntity);
            // Request body
            File file = new File("YOUR_FILE");
            FileEntity reqEntity = new FileEntity(file, "application/octet-stream");
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("REST Response:\n");
                System.out.println(json.toString(2));
            }
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }*/
    }
}