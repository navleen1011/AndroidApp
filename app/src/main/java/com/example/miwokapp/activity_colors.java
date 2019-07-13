package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class activity_colors extends AppCompatActivity {
    private MediaPlayer mp;
   /*override the method od ONcompletionListener */
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

    private final int[] resID = {R.raw.color_red, R.raw.color_green, R.raw.color_brown,
            R.raw.color_gray, R.raw.color_black, R.raw.color_white, R.raw.color_dusty_yellow,
            R.raw.color_mustard_yellow};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);


        //      get the service of the audioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<word> numbers = new ArrayList<word>();


        numbers.add(new word("Red", "wetetti", R.drawable.color_red));
        numbers.add(new word("Green", "chokokki", R.drawable.color_green));
        numbers.add(new word("Brown", "takaakki", R.drawable.color_brown));
        numbers.add(new word("Gray", "topoppi", R.drawable.color_gray));
        numbers.add(new word("Black", "kululli", R.drawable.color_black));
        numbers.add(new word("White", "kelelli", R.drawable.color_white));
        numbers.add(new word("Dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow));
        numbers.add(new word("Mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow));

        wordAdapter itemsAdapter = new wordAdapter(this, numbers, R.color.category_colors);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        mp = new MediaPlayer();

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
                    mp = MediaPlayer.create(getApplicationContext(), resID[position]);
                    mp.start();
                    mp.setOnCompletionListener(onCompletionListener);
                }


            }
        });

    }

    private void release() {
        if (mp != null) {
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