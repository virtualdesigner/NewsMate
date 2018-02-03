package com.example.android.newsmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SMDEEPAK on 28-01-2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Context context, ArrayList<Word> newsList){super(context,0, newsList);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View listItemView = convertView;


            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            Word currentWord = getItem(position);
        if(currentWord.getTitle().length()>4) {
            TextView title = (TextView) listItemView.findViewById(R.id.title);
            title.setText(currentWord.getTitle());
        }

            if(currentWord.getDescription().length()>4) {
                TextView description = (TextView) listItemView.findViewById(R.id.description);
                description.setText(currentWord.getDescription());
            }



        return listItemView;
    }
}
