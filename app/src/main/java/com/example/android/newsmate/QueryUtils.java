package com.example.android.newsmate;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMDEEPAK on 28-01-2018.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils(){
        // A constructor should be created to avoid others create their own constructor functions.
    }

    public static List<Word> fetchNewsDataFromUrl(String Url){
        String jsonString = null;
        URL url = null;
        url = createUrl(Url);
        try {
            jsonString = makeHttpRequest(url);
        }catch (IOException e){
           Log.v(LOG_TAG,"Problem making the HTTP request.",e);
        }

        List<Word> newsObject = extractFeatureFromJson(jsonString);
        return newsObject;
    }

    private static List<Word> extractFeatureFromJson(String jsonString){

        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        List<Word> newsObject = new ArrayList<>();
        String author = "";
        String url = "";
        String description = "";
        String title = "";
        String imageUrl = "";
        //Bitmap image = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for(int i = 0; i<jsonArray.length();i++){
                JSONObject articles = jsonArray.getJSONObject(i);
                author = articles.getString("author");
                title = articles.getString("title");
                url = articles.getString("url");
                description = articles.getString("description");
                newsObject.add( new Word(author,title, url, description));
            }
        }catch (JSONException e){
            Log.v(LOG_TAG,"JSON EXCEPTION",e);
        }

        return newsObject;
    }


    private static String makeHttpRequest(URL url) throws IOException{

        String jsonString = "";

        if (url == null) {
            return jsonString;
        }

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;


        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonString = getStringFromInputStream(inputStream);
            }else{
                Log.e(LOG_TAG , "Error Response Code:"+httpURLConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.v(LOG_TAG, "Problem retrieving json results", e);
        }finally{
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonString;
    }

    private static URL createUrl(String url){
        URL Url = null;
        if(url == null){
            return null;
        }

        try {
            Url = new URL(url);
        }catch (MalformedURLException e){
            Log.v(LOG_TAG, "Malformed url",e);
        }
        return Url;
    }

    private static String getStringFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder jsonString = new StringBuilder();
        String line = "";
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null) {
                jsonString.append(line);
                line = bufferedReader.readLine();
            }
        }
        return jsonString.toString();
    }
}
