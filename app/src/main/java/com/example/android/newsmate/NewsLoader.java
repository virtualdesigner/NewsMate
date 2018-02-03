package com.example.android.newsmate;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by SMDEEPAK on 03-02-2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<Word>> {

    private String mUrl = "";
    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Word> loadInBackground() {
        List<Word> newsList = QueryUtils.fetchNewsDataFromUrl(mUrl);
        return newsList;
    }
}
