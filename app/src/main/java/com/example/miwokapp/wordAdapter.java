package com.example.miwokapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class wordAdapter extends ArrayAdapter<word> {
    int colorId;
    public wordAdapter(Activity context, ArrayList<word> numbers,int color){

        super(context,0,numbers);
        this.colorId = color;
    }

    MediaPlayer mp = new MediaPlayer();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        word currentListView = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }


        TextView t1 = (TextView) listItemView.findViewById(R.id.miwok_1);
        t1.setText(currentListView.getMiwokWord());
        TextView t2 = (TextView) listItemView.findViewById(R.id.english_1);
        t2.setText(currentListView.getdefaultWord());

        ImageView i = (ImageView) listItemView.findViewById(R.id.image_1);

        if (currentListView.hasImage() == true) {
            i.setImageResource(currentListView.getImage());
            i.setVisibility(View.VISIBLE);
        } else {
            i.setVisibility(View.GONE);
        }

        /*set individual background colors */
        View layout1 = listItemView.findViewById(R.id.layout_1);
        int color = ContextCompat.getColor(getContext(), colorId);
        layout1.setBackgroundColor(color);

        /*for individual audio file */

        return listItemView;
    }
    }

