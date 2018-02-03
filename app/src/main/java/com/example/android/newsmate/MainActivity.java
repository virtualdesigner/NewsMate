package com.example.android.newsmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    static String text = "";
    private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=0bcabebcd0f04edfa9c6ef17bc47f088";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonView = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.inputText);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText.getText().toString();
                Intent newsIntent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(newsIntent);
            }
        });

    }

    public final static String getNewsUrl(){
        return "https://newsapi.org/v2/top-headlines?q="+text+"&apiKey=0bcabebcd0f04edfa9c6ef17bc47f088";
    }


}
