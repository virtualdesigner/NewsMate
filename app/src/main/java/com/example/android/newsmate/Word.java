package com.example.android.newsmate;

/**
 * Created by SMDEEPAK on 28-01-2018.
 */

public class Word {

    private String author = "";
    private String url = "";
    private String description = "";
    private String title = "";


    public Word(String mAuthor,String mTitle,String mUrl,String mDescription){
        author = mAuthor;
        url = mUrl;
        description = mDescription;
        title = mTitle;
        //image = mImageUrl;
    }

    public String getAuthor(){
        return author;
    }

    public String getUrl(){
        return url;
    }

    public String getDescription(){
        return description;
    }

    public String getTitle(){
        return title;
    }

}
