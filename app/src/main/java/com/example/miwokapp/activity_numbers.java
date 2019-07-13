package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class activity_numbers extends AppCompatActivity {

    private MediaPlayer mp;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    };

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mp.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                /* abandon Audio Focus too. Taken care in the release method itself*/
                release();
            }
        }
    };


    private int[] resId = {R.raw.number_one,R.raw.number_two,R.raw.number_three,R.raw.number_four,R.raw.number_five,R.raw.number_six,
    R.raw.number_seven,R.raw.number_eight,R.raw.number_nine,R.raw.number_ten};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);
//      get the service of the audioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        ArrayList<word> numbers = new ArrayList<word>();
        numbers.add(new word("One","lutti",R.drawable.number_one));
        numbers.add(new word("Two","otiiki",R.drawable.number_two));
        numbers.add(new word("Three","tolookosu",R.drawable.number_three));
        numbers.add(new word("Four","oyyisa",R.drawable.number_four));
        numbers.add(new word("Five","massokka",R.drawable.number_five));
        numbers.add(new word("Six","temmokka",R.drawable.number_six));
        numbers.add(new word("Seven","kenekaku",R.drawable.number_seven));
        numbers.add(new word("Eight","kawinta",R.drawable.number_eight));
        numbers.add(new word("Nine","wo'e",R.drawable.number_nine));
        numbers.add(new word("Ten","na'aacha",R.drawable.number_ten));

        /* LinearLayout rootview = (LinearLayout) findViewById(R.id.num);
        for(int i=0;i<10;i++) {
            TextView childview = new TextView(this);
            String s = numbers.get(i);
            childview.setText(s);
            rootview.addView(childview);
        } */
       wordAdapter itemsAdapter = new wordAdapter(this, numbers,R.color.category_numbers);
       ListView listView = (ListView) findViewById(R.id.list);
       listView.setAdapter(itemsAdapter);

       /*https://github.com/udacity/ud839_Miwok/blob/aa0a0d83fc4a036df53e297fae12b96fcde61a8a/app/
       src/main/java/com/example/android/miwok/NumbersActivity.java
       Better Function for the same
        */
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              release();
               /* before playing, get the permission to play via audio focus . If granted then only the sudio id played
                * Also, override the OnAudioFocusChange method of OnAudioFocusChane Listener (above) to tell what to do when the audio
                * focus comes to this app.*/
               int result = audioManager.requestAudioFocus(afChangeListener,
                       AudioManager.STREAM_MUSIC,
                       AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

               if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                   mp = MediaPlayer.create(getApplicationContext(), resId[position]);
                   mp.start();
                   mp.setOnCompletionListener(onCompletionListener);
               }
           }
       });

    }
    private void release(){
        if(mp != null){
            mp.release();
            mp = null;
            /* release audio focus off this app once its resources have been released */
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        release();
    }
}