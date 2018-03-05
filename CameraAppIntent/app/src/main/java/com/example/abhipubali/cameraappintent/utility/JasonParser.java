package com.example.abhipubali.cameraappintent.utility;

/**
 * Created by abhipubali on 3/5/18.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class JasonParser {
    String[] captions;
    String[] listOfItem;
    String[] listOfCategories;
    boolean isCalled;

    public String[] getCaptions()
    {
        return captions;
    }

    public String[] getListOfItem()
    {
        return listOfItem;
    }

    public String[] getListOfCategories()
    {
        return listOfCategories;
    }
    public void jsonParsing(String str) throws JSONException {
        JSONObject js = new JSONObject(str);
        JSONArray captionArr = js.getJSONObject("description").getJSONArray("captions");
        JSONArray tags = js.getJSONObject("description").getJSONArray("tags");
        JSONArray categories = js.getJSONArray("categories");
        captions = new String[captionArr.length()];
        for(int i=0;i<captionArr.length();i++)
        {
            JSONObject c = captionArr.getJSONObject(i);
            captions[i] = c.getString("text");
        }


        listOfItem = new String[tags.length()];
        for( int i=0; i<tags.length(); i++)
        {
            String c = (String) tags.get(i);
            listOfItem[i] = c.toString();
        }

        listOfCategories = new String[categories.length()];
        for(int i=0;i<categories.length();i++)
        {
            JSONObject c = categories.getJSONObject(i);
            listOfItem[i] = c.getString("name");
        }
    }


}
