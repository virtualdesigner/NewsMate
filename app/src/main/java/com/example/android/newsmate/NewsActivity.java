package com.example.android.newsmate;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    private TextView emptyTextView;
    private WordAdapter mAdapter;

    public static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);



        ListView list = (ListView) findViewById(R.id.list);

        emptyTextView = (TextView) findViewById(R.id.emptyView);

        list.setEmptyView(emptyTextView);

        mAdapter = new WordAdapter(this, new ArrayList<Word>());

        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Word currentNews = mAdapter.getItem(position);

                if (currentNews.getUrl() != null) {
                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNews.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }else{
                    Toast.makeText(NewsActivity.this, "This page does'nt exist.",Toast.LENGTH_SHORT);
                }
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);

        }else{
            //No Internet Connection
            View ProgressBar = findViewById(R.id.progressBar);
            ProgressBar.setVisibility(View.GONE);

            emptyTextView.setText("Internet Connection problem!");
        }

    }

    @Override
    public Loader<List<Word>> onCreateLoader(int id, Bundle args) {



        return new NewsLoader(this, MainActivity.getNewsUrl());
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> news) {

        View ProgressBar = findViewById(R.id.progressBar);
        ProgressBar.setVisibility(View.GONE);

        emptyTextView.setText("No News Found");

        mAdapter.clear();

        if(news!=null && !news.isEmpty()){
            mAdapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        mAdapter.clear();
    }

}
